var moviePic = document.getElementsByTagName(img);
var makeReservation = document.getElementsByClassName("makeReservation") 


moviePic.addEventListener("mouseover", () => {
    makeReservation.style.visibility = "visible";
});

