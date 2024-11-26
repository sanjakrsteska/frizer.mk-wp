document.addEventListener("DOMContentLoaded", function () {
    const selectDay = document.getElementById("chooseDay");
    const elementList = Array.from(document.querySelectorAll(".treatmentChooseTimeForm .option"));

    selectDay.addEventListener('change', (e) => {
        let selectedValue = selectDay.value;
        let first = true;
        elementList.forEach(item => {
            if (item.classList.contains(`options-${selectedValue}`)) {
                item.classList.remove("invisible-option");
                item.classList.add("visible-option");
                if (first) {
                    item.setAttribute("selected", "selected");
                    first = false;
                }
            } else {
                item.classList.remove("visible-option");
                item.classList.add("invisible-option");
                item.removeAttribute("selected");
            }
        })
    });
})