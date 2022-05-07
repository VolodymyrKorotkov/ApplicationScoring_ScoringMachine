function submitPoll(){
    document.getElementById("button-repeat-code").disabled = true;
    setTimeout(function() {
        document.getElementById("button-repeat-code").disabled = false;
    }, 50000);

}

document.getElementById("button-repeat-code").addEventListener("click", submitPoll);