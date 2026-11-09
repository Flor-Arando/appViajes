/*
 * Botón "Aplicar" para el DatePicker de PrimeFaces en modo rango.
 *
 * PrimeFaces solo dibuja los botones "Hoy" y "Limpiar" en la barra inferior
 * del calendario (opción showButtonBar). Este script añade un tercer botón,
 * "Aplicar", que cierra el calendario manteniendo las fechas ya marcadas.
 *
 * Se usa un MutationObserver porque PrimeFaces vuelve a generar el contenido
 * del panel cada vez que se selecciona un día (o cuando se repinta por AJAX),
 * así que el botón se vuelve a insertar automáticamente cuando hace falta.
 */
(function () {
    "use strict";

    var SELECTOR_PANEL = ".ui-datepicker";
    var SELECTOR_BARRA = ".ui-datepicker-buttonbar";
    var CLASE_BOTON = "ui-apply-button";

    /**
     * Localiza el widget PrimeFaces asociado al panel del calendario.
     */
    function buscarWidget(panel) {
        var widgets = window.PrimeFaces && PrimeFaces.widgets;
        if (!widgets) {
            return null;
        }

        for (var nombre in widgets) {
            if (Object.prototype.hasOwnProperty.call(widgets, nombre)) {
                var widget = widgets[nombre];
                if (widget && widget.panel && widget.panel.length && widget.panel[0] === panel) {
                    return widget;
                }
            }
        }
        return null;
    }

    /**
     * Añade el botón "Aplicar" a la barra inferior del calendario.
     */
    function completarBarra(barra) {
        if (barra.querySelector("." + CLASE_BOTON)) {
            return;
        }

        var fila = barra.firstElementChild;
        if (!fila) {
            return;
        }

        // Reparte la barra en tres columnas iguales: Hoy | Limpiar | Aplicar
        for (var i = 0; i < fila.children.length; i++) {
            fila.children[i].classList.remove("ui-g-6", "col-6");
            fila.children[i].classList.add("ui-g-4", "col-4");
        }

        var columna = document.createElement("div");
        columna.className = "ui-g-4 col-4";

        var boton = document.createElement("button");
        boton.type = "button";
        boton.title = "Aplicar las fechas seleccionadas";
        boton.className = CLASE_BOTON
                + " ui-button ui-widget ui-state-default ui-button-text-only"
                + " btn bg-success text-white border-0 w-100";

        var texto = document.createElement("span");
        texto.className = "ui-button-text";
        texto.textContent = "Aplicar";

        boton.appendChild(texto);
        columna.appendChild(boton);
        fila.appendChild(columna);
    }

    /**
     * Recorre un nodo (y sus descendientes) buscando barras de botones.
     */
    function procesar(raiz) {
        var barras = [];
        if (raiz.nodeType === 1 && raiz.matches(SELECTOR_BARRA)) {
            barras.push(raiz);
        }
        if (raiz.querySelectorAll) {
            barras = barras.concat(Array.prototype.slice.call(raiz.querySelectorAll(SELECTOR_BARRA)));
        }

        for (var i = 0; i < barras.length; i++) {
            completarBarra(barras[i]);
        }
    }

    /**
     * Cierra el calendario al pulsar "Aplicar".
     */
    function alPulsarAplicar(evento) {
        var boton = evento.target.closest ? evento.target.closest("." + CLASE_BOTON) : null;
        if (!boton) {
            return;
        }

        evento.preventDefault();

        var panel = boton.closest(SELECTOR_PANEL);
        var widget = panel ? buscarWidget(panel) : null;
        if (widget) {
            widget.hide();
        }
    }

    function vigilarPaneles() {
        // Paneles ya creados antes de cargar este script
        procesar(document);

        if (!window.MutationObserver) {
            return;
        }

        new MutationObserver(function (mutaciones) {
            for (var i = 0; i < mutaciones.length; i++) {
                var nodos = mutaciones[i].addedNodes;
                for (var j = 0; j < nodos.length; j++) {
                    if (nodos[j].nodeType === 1) {
                        procesar(nodos[j]);
                    }
                }
            }
        }).observe(document.body, { childList: true, subtree: true });
    }

    document.addEventListener("click", alPulsarAplicar);

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", vigilarPaneles);
    } else {
        vigilarPaneles();
    }
})();
