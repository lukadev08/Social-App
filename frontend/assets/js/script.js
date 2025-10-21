const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

const mode = document.getElementById('mode_icon_login');

mode.addEventListener('click', () => {
    const form = document.getElementById('login_form');
    if (mode.classList.contains('fa-moon')) {
        mode.classList.remove('fa-moon');
        mode.classList.add('fa-sun');

        form.classList.add('dark');
        return;
    }
    mode.classList.add('fa-moon');
    mode.classList.remove('fa-sun');
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

        // Captura os valores do formulário
        const userEmail = document.getElementById('userEmail').value;
        const password = document.getElementById('password').value;

        // Referência ao botão para feedback visual
        const button = document.getElementById('login_button');
        button.classList.add('sending');
        button.textContent = 'Logando...';
        button.disabled = true;

        // Faz a requisição de login para o backend
        fetch('http://127.0.0.1:8081/login/retrieve', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ userEmail, password })
        })
            .then(response => {
                // Verifica se a resposta foi bem-sucedida
                if (response.ok) {
                    return response.json(); // Espera receber dados do usuário em JSON
                } else {
                    // Se falhou, lança um erro que será capturado pelo catch
                    throw new Error('Credenciais inválidas');
                }
            })
            .then(userData => {
                // Login bem-sucedido! userData deve conter informações como nome e email
                console.log('Login realizado com sucesso:', userData);
                
                // Armazena os dados do usuário para usar na próxima página
                // Nota: localStorage é usado aqui, mas você mencionou que não pode usar
                // Alternativa: passar dados via URL ou sessionStorage se disponível
                localStorage.setItem('userName', userData.name);
                localStorage.setItem('userEmail', userData.email);
                
                // Feedback visual de sucesso
                button.classList.remove('sending');
                button.classList.add('success');
                button.textContent = 'Sucesso!';
                
                // Redireciona para a página de perfil após 1 segundo
                setTimeout(() => {
                    window.location.href = 'profile.html'; // Página que mostrará os dados
                }, 1000);
            })
            .catch(error => {
                // Trata erros de login
                console.error('Erro no login:', error);
                
                // Remove o estado de carregamento e mostra erro
                button.classList.remove('sending');
                button.classList.add('error');
                button.textContent = 'Erro no login';
                button.disabled = false;
                
                // Exibe mensagem de erro ao usuário
                alert('Email ou senha incorretos. Tente novamente.');
                
                // Retorna o botão ao estado normal após 2 segundos
                setTimeout(() => {
                    button.classList.remove('error');
                    button.textContent = 'Entrar';
                }, 2000);
            });
    });
});
