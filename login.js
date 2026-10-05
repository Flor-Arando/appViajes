const $ = id => document.getElementById(id);
const emailValido = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

// Usa los estados Bootstrap para mostrar errores y resaltar campos inválidos.
function mostrarError(id, mensaje) {
    const campo = $(id);
    const caja = $('error-' + id);
    if (mensaje) {
        caja.textContent = mensaje;
        campo.classList.add('is-invalid');
        return false;
    }
    campo.classList.remove('is-invalid');
    return true;
}

// Cada regla devuelve un mensaje de error, o '' si está bien
const reglas = {
    loginEmail: v => !v.trim() ? 'Escribe tu correo.' : !emailValido.test(v) ? 'El correo no es válido.' : '',
    loginPass: v => !v ? 'Escribe tu contraseña.' : ''
};

function validar(id) {
    return mostrarError(id, reglas[id]($(id).value));
}

// Valida al salir del campo, y corrige en vivo cuando ya hay error
Object.keys(reglas).forEach(id => {
    $(id).addEventListener('blur', () => validar(id));
    $(id).addEventListener('input', () => {
        if ($(id).classList.contains('is-invalid')) validar(id);
    });
});

// Envío del formulario
$('formLogin').addEventListener('submit', e => {
    e.preventDefault(); // evita que se recargue la página
    const ok = ['loginEmail', 'loginPass'].map(validar).every(Boolean);
    $('okLogin').textContent = ok ? 'Sesión iniciada correctamente.' : '';
});