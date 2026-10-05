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

// Cada regla devuelve un mensaje de error o si está bien
const reglas = {
    regNombre: v => v.trim().length < 3 ? 'El nombre debe tener al menos 3 caracteres.' : '',
    regEmail: v => !v.trim() ? 'Escribe tu correo.' : !emailValido.test(v) ? 'El correo no es válido.' : '',
    regPass: v => v.length < 8 ? 'Debe tener al menos 8 caracteres.'
        : !/[A-Z]/.test(v) ? 'Incluye al menos una mayúscula.'
            : !/\d/.test(v) ? 'Incluye al menos un número.' : '',
    regPass2: v => v !== $('regPass').value ? 'Las contraseñas no coinciden.' : ''
};

function validar(id) {
    return mostrarError(id, reglas[id]($(id).value));
}

// Valida al salir del campo y corrige en vivo cuando ya hay error
Object.keys(reglas).forEach(id => {
    $(id).addEventListener('blur', () => validar(id));
    $(id).addEventListener('input', () => {
        if ($(id).classList.contains('is-invalid')) validar(id);
    });
});

// Si cambia la contraseña, vuelve a comprobar que coincidan
$('regPass').addEventListener('input', () => {
    if ($('regPass2').value) validar('regPass2');
});

$('regTerminos').addEventListener('change', () => mostrarError('regTerminos', ''));

// Envío del formulario
$('formRegistro').addEventListener('submit', e => {
    e.preventDefault(); // evita que se recargue la página
    const camposOk = ['regNombre', 'regEmail', 'regPass', 'regPass2'].map(validar).every(Boolean);
    const terminosOk = mostrarError('regTerminos', $('regTerminos').checked ? '' : 'Debes aceptar los términos.');
    $('okRegistro').textContent = (camposOk && terminosOk) ? 'Cuenta creada. ¡Buen viaje!' : '';
});