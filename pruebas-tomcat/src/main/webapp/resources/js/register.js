const $ = id => document.getElementById(id);
const emailValido = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

// Muestra las mismas indicaciones que el formulario original.
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

Object.keys(reglas).forEach(id => {
    $(id).addEventListener('blur', () => validar(id));
    $(id).addEventListener('input', () => {
        if ($(id).classList.contains('is-invalid')) validar(id);
    });
});

$('regPass').addEventListener('input', () => {
    if ($('regPass2').value) validar('regPass2');
});

$('regTerminos').addEventListener('change', () => mostrarError('regTerminos', ''));

// Solo cancela el envío cuando los datos locales son inválidos.
$('formRegistro').addEventListener('submit', e => {
    const camposOk = ['regNombre', 'regEmail', 'regPass', 'regPass2'].map(validar).every(Boolean);
    const terminosOk = mostrarError('regTerminos', $('regTerminos').checked ? '' : 'Debes aceptar los términos.');
    if (!camposOk || !terminosOk) e.preventDefault();
});
