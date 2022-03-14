function chooseFile() {
    document.getElementById('file-input').click();
}

document.getElementById('file-input').onchange = function (event) {
    document.getElementById('image-change').setAttribute('src', URL.createObjectURL(event.target.files[0]))
}

function saveProfile() {
    showToast({
        type: 'success',
        message: 'You already changed profile successfully!',
        duration: 4000
    });
}

function saveAvatar() {
    showToast({
        type: 'success',
        message: 'You already changed avatar successfully!',
        duration: 4000
    });
}