/*
 * pago.js (versión Jakarta Faces)
 *
 * Se ha movido de la raíz del proyecto a src/main/webapp/resources/js para que
 * Jakarta Faces lo sirva con <h:outputScript library="js" name="pago.js"/>.
 *
 * Todo va dentro de un IIFE a propósito: antes el archivo declaraba `const $`
 * en el ámbito global y eso tapaba el `$` de jQuery que usa PrimeFaces
 * (PrimeFaces.ab fallaba con "$.Deferred is not a function" y el botón hacía un
 * envío normal de la página). Ahora `$` es privado y solo se exponen las dos
 * funciones que necesitan los atributos onstart.
 *
 * Mantiene la funcionalidad de pago.html: formato automático de los campos,
 * validación con mensajes en cada campo y un aviso resumen. Ya no intercepta el
 * envío: el pago lo procesa PagoBean y estas funciones se usan en onstart,
 * devolviendo false para no enviar el formulario cuando hay errores.
 */

(function () {
    'use strict';

    const $ = id => document.getElementById(id);
    const emailValido = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

    // Usa los estados Bootstrap para mostrar errores y resaltar campos inválidos.
    function mostrarError(id, mensaje) {
        const campo = $(id);
        const caja = $('error-' + id);
        if (!campo || !caja) {
            return true;
        }
        if (mensaje) {
            caja.textContent = mensaje;
            campo.classList.add('is-invalid');
            return false;
        }
        campo.classList.remove('is-invalid');
        return true;
    }

    // Aviso resumen que aparece encima del formulario cuando algo falla.
    function mostrarAviso(id, texto) {
        const aviso = $(id);
        if (!aviso) {
            return;
        }
        aviso.textContent = texto || '';
        aviso.classList.toggle('d-none', !texto);
    }

    // Algoritmo de Luhn: comprueba que el número de tarjeta sea coherente
    function luhn(numero) {
        let suma = 0, doble = false;
        for (let i = numero.length - 1; i >= 0; i--) {
            let d = Number(numero[i]);
            if (doble) { d *= 2; if (d > 9) d -= 9; }
            suma += d;
            doble = !doble;
        }
        return suma % 10 === 0;
    }

    // Cada regla devuelve un mensaje de error, o '' si está bien
    const reglas = {
        titular: v => v.trim().length < 3 ? 'Escribe el nombre del titular.' : '',
        numero: v => {
            const n = v.replace(/\s/g, '');
            if (!n) return 'Escribe el número de tarjeta.';
            if (n.length < 13 || n.length > 19) return 'El número debe tener entre 13 y 19 dígitos.';
            return luhn(n) ? '' : 'El número de tarjeta no es válido.';
        },
        vencimiento: v => {
            if (!/^\d{2}\/\d{2}$/.test(v)) return 'Usa el formato MM/AA.';
            const mes = Number(v.slice(0, 2));
            const anio = 2000 + Number(v.slice(3));
            if (mes < 1 || mes > 12) return 'El mes no es válido.';
            const hoy = new Date();
            const vence = new Date(anio, mes); // primer día del mes siguiente
            return vence <= hoy ? 'La tarjeta está vencida.' : '';
        },
        cvv: v => !/^\d{3,4}$/.test(v) ? 'El CVV tiene 3 o 4 dígitos.' : '',
        email: v => !v.trim() ? 'Escribe tu correo.' : !emailValido.test(v) ? 'El correo no es válido.' : ''
    };

    function validar(id) {
        return mostrarError(id, reglas[id]($(id).value));
    }

    function validarEmailTransferencia() {
        return mostrarError(
            'emailTransferencia',
            emailValido.test($('emailTransferencia').value.trim()) ? '' : 'Escribe un correo válido.'
        );
    }

    // Validación del pago con tarjeta. Devuelve false para cancelar el envío (onstart).
    function validarPagoTarjeta() {
        if (!$('titular')) {
            return false;
        }
        const camposOk = Object.keys(reglas).map(validar).every(Boolean);
        const terminosOk = mostrarError('terminos', $('terminos').checked ? '' : 'Debes aceptar las condiciones.');
        const correcto = camposOk && terminosOk;
        mostrarAviso('avisoPago', correcto
            ? ''
            : 'Revisa los datos marcados en rojo: faltan campos obligatorios o no son válidos.');
        return correcto;
    }

    // Validación de la transferencia. Devuelve false para cancelar el envío (onstart).
    function validarTransferencia() {
        if (!$('emailTransferencia')) {
            return false;
        }
        const emailOk = validarEmailTransferencia();
        const confirmacionOk = mostrarError(
            'confirmoTransferencia',
            $('confirmoTransferencia').checked ? '' : 'Confirma que has realizado la transferencia.'
        );
        const correcto = emailOk && confirmacionOk;
        mostrarAviso('avisoTransferencia', correcto
            ? ''
            : 'Revisa los datos marcados en rojo antes de confirmar la transferencia.');
        return correcto;
    }

    // Se ejecuta al cargar la página: formato de los campos y validación en vivo.
    function iniciarPago() {
        // Si la página no tiene el formulario de pago (no hay paquete), no hace nada.
        if (!$('numero') || !$('vencimiento') || !$('cvv')) {
            return;
        }

        Object.keys(reglas).forEach(id => {
            $(id).addEventListener('blur', () => validar(id));
            $(id).addEventListener('input', () => {
                if ($(id).classList.contains('is-invalid')) validar(id);
            });
        });

        // Formato automático: número en grupos de 4 y vencimiento con "/"
        $('numero').addEventListener('input', e => {
            const solo = e.target.value.replace(/\D/g, '').slice(0, 16);
            e.target.value = solo.replace(/(.{4})/g, '$1 ').trim();
        });
        $('vencimiento').addEventListener('input', e => {
            const solo = e.target.value.replace(/\D/g, '').slice(0, 4);
            e.target.value = solo.length > 2 ? solo.slice(0, 2) + '/' + solo.slice(2) : solo;
        });
        $('cvv').addEventListener('input', e => {
            e.target.value = e.target.value.replace(/\D/g, '');
        });
        $('terminos').addEventListener('change', () => mostrarError('terminos', ''));

        const emailTransferencia = $('emailTransferencia');
        if (emailTransferencia) {
            emailTransferencia.addEventListener('blur', validarEmailTransferencia);
            emailTransferencia.addEventListener('input', () => {
                if (emailTransferencia.classList.contains('is-invalid')) validarEmailTransferencia();
            });
        }
        if ($('confirmoTransferencia')) {
            $('confirmoTransferencia').addEventListener('change', () => {
                mostrarError('confirmoTransferencia', '');
            });
        }
    }

    // El script se carga en <h:head>, así que esperamos al DOM para conectar los campos.
    document.addEventListener('DOMContentLoaded', iniciarPago);

    // Solo estas dos funciones se usan desde los onstart de pago.xhtml.
    window.validarPagoTarjeta = validarPagoTarjeta;
    window.validarTransferencia = validarTransferencia;
})();
