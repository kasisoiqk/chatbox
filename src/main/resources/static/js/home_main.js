var users = [
    {
        id: 1,
        name: 'Ana Pink',
        avatar: 'user1.jpg',
        isNew: true,
        isSender: false,
        isFavourite: true,
        isGroup: false,
        content: 'Hello!',
        time: '3 min'
    },
    {
        id: 2,
        name: 'Kela Bana',
        avatar: 'user2.jpg',
        isNew: false,
        isSender: true,
        isFavourite: true,
        isGroup: false,
        content: 'How are you?',
        time: '1 hour'
    },
    {
        id: 3,
        name: 'Wibu Family',
        avatar: 'user3.jpg',
        isNew: false,
        isSender: true,
        isFavourite: false,
        isGroup: true,
        content: 'Hahahaaa...',
        time: '2 hour'
    },
    {
        id: 4,
        name: 'Hana Tie',
        avatar: 'user4.jpg',
        isNew: true,
        isSender: false,
        isFavourite: true,
        isGroup: false,
        content: 'What are you doing?',
        time: '5 hour'
    }
];

var listMember = [];

let headerUser = document.querySelector('.app-header__right');

// Load list user messages
function loadUserMessages() {
    var userDOM = document.querySelector('.app-body-navbar-content__list-users');
    userDOM.innerHTML = '';

    users.forEach(function(user) {
        let element = document.createElement('li');
        element.classList.add('app-body-navbar-content__item-user');

        element.innerHTML = `
        <img class="app-body-navbar-content-item-user__img"
            src="./assets/images/users/${user.avatar}" alt="${user.name}">
        <div class="app-body-navbar-content-item-user__name">
            <h2>${user.name}</h2>
            <p>${user.isSender ? 'You':user.name}: ${user.content}</p>
        </div>
        <div class="app-body-navbar-content-item-user__time">${user.time}</div>
        `;

        if(user.isNew) {
            let unread = document.createElement('div');
            unread.classList.add('app-body-navbar-content-item-user__unread');
            unread.innerText = '●';
            element.appendChild(unread);
            element.classList.add('item-user--message-unread');
        }
        userDOM.appendChild(element);
    });
}

function loadUserFavorite() {
    var favouriteDOM = document.querySelector('.app-header-body-navbar-modal-group__favourite ul');
    favouriteDOM.innerHTML = '';

    users.forEach(function(user) {
        if (user.isFavourite) {
            favouriteDOM.innerHTML += `
            <li onclick='addMember(${user.id})'>
                <img src="./assets/images/users/${user.avatar}" alt="${user.name}">
                ${user.name}
            </li>
            `;
        }
    });
}

function loadMember() {
    let favouriteDOM = document.querySelector('.app-header-body-navbar-modal-group__user ul');
    favouriteDOM.innerHTML = '';

    listMember.forEach(function(member) {
        let element = document.createElement('li');
        element.innerHTML = `
        <span>${member.name}</span>
        <i class="fa-solid fa-xmark" onclick="removeMember(${member.id})"></i>
        `;

        favouriteDOM.appendChild(element);
    });
}

function addMember(id) {
    let user = users.find(function(user) {
        return user.id === id;
    });

    if (user != null) {
        listMember.push(user);
        loadMember();
    }
}

function removeMember(id) {
    listMember = listMember.filter(function(member) {
        return member.id != id;
    });
    loadMember();
    document.querySelector('#modal-create-group').checked = false;
}

function logout() {
    headerUser.innerHTML = `
    <label for="modal-login" class="app-header-right__login">
        <i class="fa-solid fa-right-to-bracket"></i>
    </label>
    `;

    showToast({
        message: 'You are logged out of the browser!',
        type: 'success',
        duration: 4000
    });
}

document.querySelector('#login-submit').addEventListener('click', function() {
    headerUser.innerHTML = `
    <div class="app-header-right__user">
        <div class="app-header-right__user-wrapper">
            <div class="app-header-right-user__avatar">
                <img src="./assets/images/users/user.jpg" alt="user">
            </div>
            <div class="app-header-right-user__name">I'MKAI</div>
            <ul class="app-header-right__user-profile">
                <li>View Profile</li>
                <li onclick="logout()">Logout</li>
            </ul>
        </div>
        <div class="app-header-right-user__logout" onclick="logout()">
            <i class="fa-solid fa-right-from-bracket"></i>
        </div>
    </div>
    `;

    showToast({
        message: 'Logged in successfully! Now you can chat with others.',
        type: 'success',
        duration: 4000
    });
    document.querySelector('#modal-login').checked = false;
});

// Call function
loadUserMessages();
loadUserFavorite();