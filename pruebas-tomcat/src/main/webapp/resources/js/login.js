const $ = id => document.getElementById(id);
const usuarioValido = /^[^\s]+$/;

// Muestra mensajes locales sin sustituir la validación del servidor.
function mostrarError(id, mensaje) {
    const campo = $(id);
    const caja = $('error-' + id);
    if (mensaje) {
        caja.textContent = mensaje;
        campo.classList.add('is-invalid');
        return false;
    }
    campo.classList.remove('is-invalid');
    caja.textContent = '';
    return true;
}

const reglas = {
    loginEmail: v => !v.trim() ? 'Escribe tu usuario.' : !usuarioValido.test(v) ? 'El usuario no es válido.' : '',
    loginPass: v => !v ? 'Escribe tu contraseña.' : ''
};

function validar(id) {
    return mostrarError(id, reglas[id]($(id).value));
}

Object.keys(reglas).forEach(id => {
    $(id).addEventListener('blur', () => validar(id));
    $(id).addEventListener('input', () => {
        if ($(id).classList.contains('is-invalid')) validar(id);
    });
});

// Solo cancela el envío cuando los datos locales son inválidos.
$('formLogin').addEventListener('submit', e => {
    if (!['loginEmail', 'loginPass'].map(validar).every(Boolean)) e.preventDefault();
});
