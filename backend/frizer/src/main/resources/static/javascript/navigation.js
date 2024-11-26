/*=============== SHOW MENU ===============*/
const navMenu = document.getElementById('nav-menu'),
    navToggle = document.getElementById('nav-toggle'),
    navClose = document.getElementById('nav-close'),
    advancedToggle = document.getElementById('advanced-toggle'),
    advancedMenu = document.getElementById('advanced-menu'),
    arrow = document.querySelector('#advanced-toggle i')


/* Menu show */
if (navToggle) {
    navToggle.addEventListener('click', () => {
        navMenu.classList.add('show-menu');
    })
}

if (advancedToggle) {
    advancedToggle.addEventListener('click', () => {
       console.log(arrow);
        if (advancedMenu.classList.contains('show-advanced-menu')) {
            advancedMenu.classList.remove('show-advanced-menu');
            arrow.classList.remove('ri-arrow-up-line');
            arrow.classList.add('ri-arrow-down-line');
        } else {
            advancedMenu.classList.add('show-advanced-menu')
            arrow.classList.remove('ri-arrow-down-line');
            arrow.classList.add('ri-arrow-up-line');
        }
    })
}

/* Menu hidden */
if (navClose) {
    navClose.addEventListener('click', () => {
        navMenu.classList.remove('show-menu')
    })
}

