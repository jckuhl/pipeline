(function() {
    let counter = 4;
    const span = document.querySelector('span');
    const redirectTimer = setInterval(()=> {
        counter -= 1;
        span.innerText = counter;
        if(counter == 0) {
            clearInterval(redirectTimer);
            window.location.assign('../html/index.html');
        }
    }, 1000);

})();