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

        const fieldIds = ['name', 'userEmail', 'password']

        const button = document.getElementById('register_button');

        fetch('http://127.0.0.1:8080/auth/register', {
            method: 'POST',
            statusCode: 200,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            credentials: "include",
            body: JSON.stringify({ name, userEmail, password })
        })
            .then(response => {
                if (response.ok) {
                    return response.text(console.log('Cadastrado com sucesso'));
                } else {
                    throw new Error(alert('Erro ao cadastrar usuário: usuário já cadastrado'));
                }
            })
            .then(userData => {
                button.classList.remove('sending');
                if (typeof userData === 'string') {
                    console.log(userData);
                } else {
                    alert(userData.message);
                }

                const user = {
                    name: name,
                    email: userEmail,
                };

                localStorage.setItem('user', JSON.stringify(user));

                setTimeout(() => {
                    button.classList.add('success');
                    button.textContent = 'Sucesso!';
                }, 1000);
                setTimeout(() => {
                    button.classList.remove('success');
                    button.textContent = 'Criar uma conta';
                }, 2000);
                setTimeout(() => {
                    fieldIds.forEach(id => {
                        document.getElementById(id).value = '';
                    });
                }, 3000);
            })
            .catch(error => console.error('Erro no cadastro:', error));
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('login_form');
    const button = document.getElementById('login_button');

    const resetButton = () => {
        button.classList.remove('sending', 'success', 'error');
        button.textContent = 'Entrar';
        button.disabled = false;
    };

    loginForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const userEmail = document.getElementById('login_userEmail').value.trim();
        const password = document.getElementById('login_password').value;

        try {
            const response = await fetch('http://127.0.0.1:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                },
                credentials: "include",
                body: JSON.stringify({ userEmail, password })
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                const message = errorData?.message || `Erro ${response.status}: ${response.statusText}`;
                throw new Error(message);
            }

            const userData = await response.json();

            if (!userData?.token) {
                throw new Error('Resposta inválida do servidor');
            }

            localStorage.setItem('token', userData.token);
            localStorage.setItem('userEmail', userData.userEmail || userEmail);

            setTimeout(() => {
                button.classList.add('sending');
                button.textContent = 'Logando...';
                button.disabled = true;
            }, 1000);
            setTimeout(() => {
                button.classList.remove('sending');
                button.classList.add('success');
                button.textContent = 'Sucesso!';
            }, 3000);
            setTimeout(() => {
                window.location.href = 'profile.html';
            }, 5000);

        } catch (error) {
            console.error('Erro no login:', error);

            button.classList.remove('sending');
            button.classList.add('error');
            button.textContent = 'Erro no login';

            alert(error.message || 'Erro durante o login. Tente novamente.');

            setTimeout(resetButton, 4000);
        }
    });
});
