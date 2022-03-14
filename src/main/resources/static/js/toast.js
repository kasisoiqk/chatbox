function showToast({
    message = 'Bạn vừa thêm sản phẩm vào giỏ hàng',
    type = 'success',
    duration = 2000
}) {
    const toastDOM = document.querySelector('.toast');
    if(toastDOM) {
        var toast = document.createElement('div');
        toast.classList.add('toast__element', `toast__element--${type}`);

        // Auto remove time out
        const autoRemoveId = setTimeout(function () {
            toastDOM.removeChild(toast);
        }, duration + 500);

        // Remove toast when click
        toast.onclick = function (e) {
            if (e.target.closest('.toast__close')) {
                toastDOM.removeChild(toast);
                clearTimeout(autoRemoveId);
            }
        };

        const icons = {
            success: 'fas fa-check-circle',
            error: 'fas fa-exclamation-circle',
            info: 'fas fa-info-circle'
        };

        var icon = icons[type];
        var delay = (duration / 1000).toFixed(2);

        toast.innerHTML = `
        <div class="toast__icon">
            <i class="${icon}"></i>
        </div>
        <div class="toast__body">${message}</div>
        <div class="toast__close">
            <i class="fas fa-times"></i>
        </div>
        `;
        toast.style.animation = `toastAppear .3s cubic-bezier(0.18, 0.89, 0.32, 1.28), toastDisappear .3s ${delay}s ease forwards`;

        toastDOM.append(toast);
    }
}