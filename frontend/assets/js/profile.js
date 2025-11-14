document.addEventListener('DOMContentLoaded', () => {
    const containers = document.querySelectorAll('.profile-card-inf__img');
    if (containers.length === 0)
        return;

    containers.forEach(container => {

        const imgs = container.querySelectorAll('img');
        if (imgs.length === 0) return;

        const maxImages = 3;
        const sourceImg = imgs[0];
        let currentCount = imgs.length;

        while (currentCount < maxImages) {
            const clone = sourceImg.cloneNode(true);
            container.appendChild(clone);
            currentCount++;
        }
    });

});

document.addEventListener('DOMContentLoaded', () => {
    const userData = JSON.parse(localStorage.getItem('user'))

    if (userData && userData.name) {
        document.getElementById('userName').textContent = userData.name
    }

})