(()=>{
	
console.log("Hello world");

	const endpoint = "./some-url";
	fetch(endpoint).then(function (response) {
		if(!response.ok) {
			throw new Error("Response received is not OK: " + response.statusText);
		}
		return response.json();		
	}).then(function (data){
		console.log(JSON.stringify(data));
		
		document.getElementById("heading").textContent += data.firstName + " " + data.lastName; 
		
	}).catch(function (error) {
		console.log("There was a problem with pulling data: ", error);
	});
	
})();