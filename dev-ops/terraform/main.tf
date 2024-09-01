provider "aws" {
  region = "us-east-2"
}

data "aws_vpc" "olga_bridges_vpc" {
  default = true
}

data "aws_subnets" "olga_bridges_subnets" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.olga_bridges_vpc.id]
  }
}
//
// Create a new ECS cluster
//
resource "aws_ecs_cluster" "olga_bridges_cluster" {
  name = "olga_bridges_cluster"
}
//
// Create a new load balancer and call it 'olga_bridges_lb'
//
resource "aws_lb" "olga_bridges_lb" {
  name               = "olga-bridges-lb"// this is the name under which it will be registered in AWS
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.olga_bridges_load_balancer_sg.id]
  subnets            = data.aws_subnets.olga_bridges_subnets.ids
}
//
// Create security group for web application
//
resource "aws_security_group" "olga_bridges_sg" {
  name        = "olga-bridges-sg"
  description = "Allow all HTTP(s) traffic"
  vpc_id      = data.aws_vpc.olga_bridges_vpc.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
//
// Create security group for load balancer
//
resource "aws_security_group" "olga_bridges_load_balancer_sg" {
  name        = "load-balancer-sg"
  description = "Allow all HTTP(s) traffic"
  vpc_id      = data.aws_vpc.olga_bridges_vpc.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
//
// Task definition for wEb application
//
resource "aws_ecs_task_definition" "olga_bridges_web_td" {
  family                   = "olga-bridges-web-task-definition"
  container_definitions    = jsonencode([
    {
      name  = "olga-bridges-web-service"
      image = "alexandergarbuz/olga-bridges-web:latest" # Replace with your image
      portMappings = [
        {
          containerPort = 80
          hostPort      = 80
        }
      ]
      environment = [
        {
          name  = "SPRING_PROFILES_ACTIVE"
          value = "prod"
        },
        {
          name  = "WEB_SERVER_CONTEXT_PATH"
          value = "/"
        },
        {
          name  = "WEB_SERVER_PORT"
          value = "80"
        }
      ]
    }
  ])
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  memory                   = "512"
  cpu                      = "256"
//  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
}
//
// Web application service
//
resource "aws_ecs_service" "olga_bridges_web_service" {
  name            = "olga_bridges_web_service"
  cluster         = aws_ecs_cluster.olga_bridges_cluster.id
  task_definition = aws_ecs_task_definition.olga_bridges_web_td.arn
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets         = data.aws_subnets.olga_bridges_subnets.ids
    security_groups = [aws_security_group.olga_bridges_sg.id]
    assign_public_ip = true
  }
  load_balancer {
    target_group_arn = aws_lb_target_group.olga_bridges_lb_tg.arn
    container_name   = "olga-bridges-web-service"
    container_port   = 80
  }
  depends_on = [
    aws_lb_listener.olga_bridges_frontend_http
  ]
}
//
// Create load balancer target group
//
resource "aws_lb_target_group" "olga_bridges_lb_tg" {
  name        = "olga-bridges-lb-tg"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = data.aws_vpc.olga_bridges_vpc.id
  target_type = "ip"
}
//
// Create HTTPS listener for load balancer that will use existing SSL
// certificate (see sertificates in AWS) and reference it by its ARN
//
resource "aws_lb_listener" "olga_bridges_frontend_https" {
  load_balancer_arn = aws_lb.olga_bridges_lb.arn
  port              = 443
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = "arn:aws:acm:us-east-2:471112670997:certificate/5d123a51-6ad4-4bd6-9e37-ef001ca72296"//aws_acm_certificate.cert.arn

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.olga_bridges_lb_tg.arn
  }
}
//
// Create HTTP listener for load balancer 
//
resource "aws_lb_listener" "olga_bridges_frontend_http" {
  load_balancer_arn = aws_lb.olga_bridges_lb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.olga_bridges_lb_tg.arn
  }
}
//
// Create DNS record for the project
//
resource "aws_route53_record" "olga_bridges_dns_record" {
  zone_id = "Z052030233WIWXK1ZIRMJ"//located under hosted zone details
  name    = "olga-bridges.aws.garbuz.com"
  type    = "A"

  alias {
    name                   = aws_lb.olga_bridges_lb.dns_name
    zone_id                = aws_lb.olga_bridges_lb.zone_id
    evaluate_target_health = true
  }
}

output "olga_bridges_load_balancer_dns_name" {
  description = "The DNS name of the load balancer"
  value       = aws_lb.olga_bridges_lb.dns_name
}