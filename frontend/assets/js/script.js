const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

const login_mode = document.getElementById('mode_icon_login');

login_mode.addEventListener('click', () => {
    const form = document.getElementById('login_form');
    if (login_mode.classList.contains('fa-moon')) {
        login_mode.classList.remove('fa-moon');
        login_mode.classList.add('fa-sun');

        form.classList.add('dark');
        return;
    }
    login_mode.classList.add('fa-moon');
    login_mode.classList.remove('fa-sun');
    form.classList.remove('dark');
});

const register_mode = document.getElementById('mode_icon_register');

register_mode.addEventListener('click', () => {
    const form = document.getElementById('register_form');
    if (register_mode.classList.contains('fa-moon')) {
        register_mode.classList.remove('fa-moon');
        register_mode.classList.add('fa-sun');

        form.classList.add('dark');
        return;
    }
    register_mode.classList.add('fa-moon');
    register_mode.classList.remove('fa-sun');
    form.classList.remove('dark');
});


document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('register_form');
    registerForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const name = document.getElementById('name').value;
        const userEmail = document.getElementById('userEmail').value;
        const password = document.getElementById('password').value;

        const button = document.getElementById('register_button');
        button.classList.add('sending');
        button.textContent = 'Enviando...';
        button.disabled = true

        fetch('http://127.0.0.1:8081/register/create', {
            method: 'POST',
            statusCode: 200,
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'POST,PATCH,OPTIONS'
            },
            body: JSON.stringify({ name, userEmail, password })
        })
            .then(response => {
                if (response.ok) {
                    return response.text(console.log('Cadastrado com sucesso'));
                } else {
                    throw new Error(alert('Erro ao cadastrar usuário'));
                }
            })
            .then(data => {
                button.classList.remove('sending');
                if (typeof data === 'string') {
                    console.log(data);
                } else {
                    alert(data.message);
                }

                setTimeout(() => {
                    button.classList.add('success');
                    button.textContent = 'Sucesso!';
                }, 1000);
                setTimeout(() => {
                    button.classList.remove('success');
                    button.textContent = 'Criar uma conta';
                }, 2000);
            })
            .catch(error => console.error('Erro no cadastro:', error));
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('login_form');
    
    loginForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const userEmail = document.getElementById('userEmail').value;
        const password = document.getElementById('password').value;

        const button = document.getElementById('login_button');
        button.classList.add('sending');
        button.textContent = 'Logando...';
        button.disabled = true;

        fetch('http://127.0.0.1:8081/login/retrieve', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ userEmail, password })
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Credenciais inválidas');
                }
            })
            .then(userData => {
                console.log('Login realizado com sucesso:', userData);
                
                localStorage.setItem('userName', userData.name);
                localStorage.setItem('userEmail', userData.email);
                
                button.classList.remove('sending');
                button.classList.add('success');
                button.textContent = 'Sucesso!';
                
                setTimeout(() => {
                    window.location.href = 'profile.html'; 
                }, 1000);
            })
            .catch(error => {
                console.error('Erro no login:', error);
                
                button.classList.remove('sending');
                button.classList.add('error');
                button.textContent = 'Erro no login';
                button.disabled = false;
                
                alert('Email ou senha incorretos. Tente novamente.');
                
                setTimeout(() => {
                    button.classList.remove('error');
                    button.textContent = 'Entrar';
                }, 2000);
            });
    });
});
