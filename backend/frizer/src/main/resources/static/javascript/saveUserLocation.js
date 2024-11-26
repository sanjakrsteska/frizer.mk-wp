document.addEventListener("DOMContentLoaded", function () {
    navigator.geolocation.getCurrentPosition(
        function(position) {
            let lat1 = position.coords.latitude;
            let lon1 = position.coords.longitude;
            let value = lat1 + "," + lon1;

            // Send geolocation data to the server using fetch
            fetch('/save-user-position', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain',
                },
                body: value,
                // body: JSON.stringify({ userPosition: value }),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    // Do something with the successful response if needed
                })
                .catch(error => {
                    console.error('There was a problem with the fetch operation:', error);
                });

        },
        function(error) {
            alert("Location not available");
        },
        { timeout: 10000 } // Set a timeout of 10 seconds
    );
});