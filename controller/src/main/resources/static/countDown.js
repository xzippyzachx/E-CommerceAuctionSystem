const CountDown = ((date, element) => {
    let countDownDate = new Date(date).getTime();
    element.innerHTML = "";

    // Update the countdown every 1 second
    let x = setInterval(() => {
        Update();
    }, 1000);

    let Update = (() => {
        // Get today's date and time
        let now = new Date().getTime();

        // Find the distance between now and the countdown date
        let distance = countDownDate - now;

        // If the countdown is over, write some text
        if (distance < 0) {
            clearInterval(x);
            element.innerHTML = "";
            return;
        }

        // Time calculations for days, hours, minutes and seconds
        let days = Math.floor(distance / (1000 * 60 * 60 * 24));
        let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Output the result in an element with id="demo"
        element.innerHTML = days + "d " + hours + "h " + minutes + "m " + seconds + "s ";
    });
    Update();
});