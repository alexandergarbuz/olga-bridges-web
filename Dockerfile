# Use the official Tomcat 10.1.26 image with Java 17
FROM tomcat:10.1.26-jdk17-temurin

# Set the environment variable for the location of the WAR file
ENV WAR_FILE=target/olga-bridges-web.war

# Copy the WAR file to the webapps directory of Tomcat
COPY $WAR_FILE /usr/local/tomcat/webapps/ROOT.war

#RUN mvn clean package -Dmaven.test.skip=true
RUN sed -i 's/port="8080"/port="80"/' /usr/local/tomcat/conf/server.xml

# Expose port 80 for the Tomcat server
EXPOSE 80

# Start Tomcat
CMD ["catalina.sh", "run"]
