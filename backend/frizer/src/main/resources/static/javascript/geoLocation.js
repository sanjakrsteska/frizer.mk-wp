function updateDistance(element, id, lat2, lon2) {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            function(position) {
                let lat1 = position.coords.latitude;
                let lon1 = position.coords.longitude;

                let R = 6371; // Radius of the Earth in kilometers
                let dLat = deg2rad(lat2 - lat1);
                let dLon = deg2rad(lon2 - lon1);
                let a =
                    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
                let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                let distance = R * c; // Distance in kilometers

                element.innerText = distance.toFixed(2)+ " km";
            },
            function(error) {
                console.error("Error getting geolocation:", error);
                element.innerText = "Непознато";
            },
            { timeout: 10000 } // Set a timeout of 10 seconds
        );
    } else {
        element.innerText = "Непознато";
    }
}

function deg2rad(deg) {
    return deg * (Math.PI / 180);
}

function getCurrentLocation(){
    navigator.geolocation.getCurrentPosition(locationAllowed, locationNotAllowed);
}
function locationAllowed(){
    let el = document.querySelector("#distance");
    el.readOnly = false;
}
function locationNotAllowed(err){
    let el = document.querySelector("#distance");
    el.readOnly = true;
    if (err.code === 1) {
        alert("Ве молиме дозволете користење на локација, за користење на филтерот: Оддалеченост");
    } else {
        alert("Не може да се пристапи локацијата");
    }
}
document.addEventListener("DOMContentLoaded", function () {
    let elements= document.querySelectorAll(".salonDistance");
    elements.forEach(el => {
        let id = parseFloat(el.getAttribute('data-salon-id'));
        let latitude = parseFloat(el.getAttribute('data-salon-lat'));
        let longitude = parseFloat(el.getAttribute('data-salon-lng'));
        updateDistance(el, id, latitude, longitude);
    })
    // sendDistancesToServer();
});