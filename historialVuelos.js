// aqui pongo unos vuelos de ejemplo para probar el historial

const vuelosDeEjemplo = [
    {
        id: 1,
        origen: 'Madrid',
        destino: 'Roma',
        ciudad: 'Roma',
        pais: 'Italia',
        continente: 'europa',
        fecha: '2026-12-15',
        estado: 'confirmado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 180,
        imagen: 'https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 2,
        origen: 'Madrid',
        destino: 'Tokio',
        ciudad: 'Tokio',
        pais: 'Japón',
        continente: 'asia',
        fecha: '2027-01-20',
        estado: 'pendiente',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 740,
        imagen: 'https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 3,
        origen: 'Madrid',
        destino: 'Nueva York',
        ciudad: 'Nueva York',
        pais: 'EE. UU.',
        continente: 'norteamerica',
        fecha: '2026-11-10',
        estado: 'confirmado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 520,
        imagen: 'https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 4,
        origen: 'Madrid',
        destino: 'Cusco',
        ciudad: 'Cusco',
        pais: 'Perú',
        continente: 'sudamerica',
        fecha: '2026-09-05',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 680,
        imagen: 'https://images.unsplash.com/photo-1526392060635-9d6019884377?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 5,
        origen: 'Madrid',
        destino: 'Sídney',
        ciudad: 'Sídney',
        pais: 'Australia',
        continente: 'oceania',
        fecha: '2027-03-12',
        estado: 'pendiente',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 920,
        imagen: 'https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 6,
        origen: 'Madrid',
        destino: 'El Cairo',
        ciudad: 'El Cairo',
        pais: 'Egipto',
        continente: 'africa',
        fecha: '2026-08-18',
        estado: 'cancelado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 430,
        imagen: 'https://images.unsplash.com/photo-1539650116574-8efeb43e2750?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 7,
        origen: 'Madrid',
        destino: 'Lisboa',
        ciudad: 'Lisboa',
        pais: 'Portugal',
        continente: 'europa',
        fecha: '2026-08-19',
        visita: '2 meses',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 95,
        imagen: 'https://images.unsplash.com/photo-1555881400-74d7acaacd8b?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 8,
        origen: 'Madrid',
        destino: 'París',
        ciudad: 'París',
        pais: 'Francia',
        continente: 'europa',
        fecha: '2026-03-19',
        visita: '7 meses',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 160,
        imagen: 'https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 9,
        origen: 'Madrid',
        destino: 'Ámsterdam',
        ciudad: 'Ámsterdam',
        pais: 'Países Bajos',
        continente: 'europa',
        fecha: '2025-08-19',
        visita: '1 año y 2 meses',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 190,
        imagen: 'https://images.unsplash.com/photo-1512470876302-972faa2aa9a4?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 10,
        origen: 'Madrid',
        destino: 'Tenerife',
        ciudad: 'Tenerife',
        pais: 'España',
        continente: 'europa',
        fecha: '2024-12-19',
        visita: '1 año y 10 meses',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 120,
        imagen: 'https://images.unsplash.com/photo-1667930578642-42b99abf25f5?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 11,
        origen: 'Madrid',
        destino: 'Barcelona',
        ciudad: 'Barcelona',
        pais: 'España',
        continente: 'europa',
        fecha: '2024-04-19',
        visita: '2 años y 6 meses',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 85,
        imagen: 'https://images.unsplash.com/photo-1578095172812-dcc191c5aed8?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 12,
        origen: 'Madrid',
        destino: 'Londres',
        ciudad: 'Londres',
        pais: 'Reino Unido',
        continente: 'europa',
        fecha: '2023-10-19',
        visita: '3 años',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 210,
        imagen: 'https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 13,
        origen: 'Madrid',
        destino: 'Nueva York',
        ciudad: 'Nueva York',
        pais: 'Estados Unidos',
        continente: 'norteamerica',
        fecha: '2022-10-19',
        visita: '4 años',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 520,
        imagen: 'https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 14,
        origen: 'Madrid',
        destino: 'Sevilla',
        ciudad: 'Sevilla',
        pais: 'España',
        continente: 'europa',
        fecha: '2021-10-19',
        visita: '5 años',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 75,
        imagen: 'https://images.unsplash.com/photo-1539037116277-4db20889f2d4?auto=format&fit=crop&w=800&q=80'
    },
    {
        id: 15,
        origen: 'Madrid',
        destino: 'Ecuador',
        ciudad: 'Parque Nacional Cotopaxi',
        pais: 'Ecuador',
        continente: 'sudamerica',
        fecha: '2024-06-19',
        visita: '2 años y 4 meses',
        estado: 'completado',
        aerolinea: 'MorfeoTrip (ejemplo)',
        precio: 890,
        imagen: 'https://images.unsplash.com/photo-1719451015082-ac0c6a18bf73?auto=format&fit=crop&w=800&q=80'
    }
];

// esta clave es el nombre con el que guardo los vuelos en el navegador
const CLAVE_HISTORIAL = 'historialVuelos';
let vuelos = [];
let continenteSeleccionado = 'todos';

// funcion para cargar los vuelos guardados
function cargarVuelos() {
    const datosGuardados = localStorage.getItem(CLAVE_HISTORIAL);

    // si es la primera vez, guardo los vuelos de ejemplo
    if (datosGuardados === null) {
        localStorage.setItem(CLAVE_HISTORIAL, JSON.stringify(vuelosDeEjemplo));
        return vuelosDeEjemplo;
    }

    // convierto el texto guardado en una lista de vuelos
    try {
        const vuelosGuardados = JSON.parse(datosGuardados);

        // añado los ejemplos nuevos sin borrar los cambios que ya se guardaron
        vuelosDeEjemplo.forEach(function (vueloEjemplo) {
            let yaEstaGuardado = false;

            vuelosGuardados.forEach(function (vueloGuardado) {
                if (vueloGuardado.id === vueloEjemplo.id) {
                    yaEstaGuardado = true;
                    vueloGuardado.imagen = vueloEjemplo.imagen;
                }
            });

            if (!yaEstaGuardado) {
                vuelosGuardados.push(vueloEjemplo);
            }
        });

        localStorage.setItem(CLAVE_HISTORIAL, JSON.stringify(vuelosGuardados));
        return vuelosGuardados;
    } catch (error) {
        return vuelosDeEjemplo;
    }
}

// funcion para guardar los cambios en el navegador
function guardarVuelos() {
    localStorage.setItem(CLAVE_HISTORIAL, JSON.stringify(vuelos));
}

// paso la fecha al formato que se usa en español
function mostrarFecha(fecha) {
    if (!fecha) {
        return 'Fecha sin confirmar';
    }

    return new Date(fecha + 'T00:00:00').toLocaleDateString('es-ES');
}

// aqui creo la tarjeta de cada vuelo y sus botones
function crearTarjeta(vuelo) {
    const tarjeta = document.createElement('article');
    tarjeta.className = 'col-12 col-md-6 col-lg-4';

    // el color de la etiqueta depende del estado del vuelo
    const estado = document.createElement('span');
    estado.className = 'badge rounded-pill';
    estado.textContent = vuelo.estado.charAt(0).toUpperCase() + vuelo.estado.slice(1);

    if (vuelo.estado === 'confirmado') {
        estado.classList.add('text-bg-success');
    } else if (vuelo.estado === 'pendiente') {
        estado.classList.add('text-bg-warning');
    } else if (vuelo.estado === 'cancelado') {
        estado.classList.add('text-bg-danger');
    } else {
        estado.classList.add('text-bg-secondary');
    }

    // imagen, ruta, fecha y datos del vuelo
    const imagen = document.createElement('img');
    imagen.src = vuelo.imagen;
    imagen.alt = 'Destino: ' + vuelo.ciudad;
    imagen.className = 'card-img-top img-vuelo rounded-top-4';

    const titulo = document.createElement('h5');
    titulo.className = 'card-title fw-bold';
    titulo.textContent = vuelo.origen + ' → ' + vuelo.destino;

    const fecha = document.createElement('p');
    fecha.className = 'text-secondary mb-1';
    if (vuelo.visita) {
        fecha.textContent = 'Visitado hace ' + vuelo.visita;
    } else {
        fecha.textContent = 'Salida: ' + mostrarFecha(vuelo.fecha);
    }

    const aerolinea = document.createElement('p');
    aerolinea.className = 'text-secondary mb-1';
    aerolinea.textContent = 'Aerolínea: ' + vuelo.aerolinea;

    const precio = document.createElement('p');
    precio.className = 'text-secondary';
    precio.textContent = 'Precio: ' + vuelo.precio + ' €';

    // boton para abrir la ventana con los detalles
    const botonDetalles = document.createElement('button');
    botonDetalles.className = 'btn btn-outline-primary btn-sm rounded-pill me-2';
    botonDetalles.textContent = 'Ver detalles';
    botonDetalles.addEventListener('click', function () {
        mostrarDetalles(vuelo);
    });

    // boton para descargar la informacion del vuelo
    const botonDescargar = document.createElement('button');
    botonDescargar.className = 'btn btn-outline-secondary btn-sm rounded-pill me-2';
    botonDescargar.textContent = 'Descargar';
    botonDescargar.addEventListener('click', function () {
        descargarItinerario(vuelo);
    });

    const contenido = document.createElement('div');
    contenido.className = 'card-body';
    contenido.append(titulo, estado, fecha, aerolinea, precio, botonDetalles, botonDescargar);

    // solo se puede cancelar si sigue pendiente o confirmado
    if (vuelo.estado === 'pendiente' || vuelo.estado === 'confirmado') {
        const botonCancelar = document.createElement('button');
        botonCancelar.className = 'btn btn-outline-danger btn-sm rounded-pill';
        botonCancelar.textContent = 'Cancelar';
        botonCancelar.addEventListener('click', function () {
            cancelarVuelo(vuelo.id);
        });
        contenido.appendChild(botonCancelar);
    }

    const caja = document.createElement('div');
    caja.className = 'card h-100 border-0 shadow-sm rounded-4 tarjeta-vuelo';
    caja.append(imagen, contenido);
    tarjeta.appendChild(caja);

    return tarjeta;
}

// busco los vuelos que coinciden con los filtros y los enseño
function mostrarVuelos() {
    const galeria = document.getElementById('gallery');
    const texto = document.getElementById('searchInput').value.toLowerCase();
    const fechaDesde = document.getElementById('fechaDesde').value;
    const fechaHasta = document.getElementById('fechaHasta').value;
    const estado = document.getElementById('filtroEstado').value;
    const mensajeSinResultados = document.getElementById('noResults');
    const historialVacio = document.getElementById('emptyHistory');
    const contador = document.getElementById('resultCount');

    // borro las tarjetas antiguas antes de dibujar las nuevas
    galeria.innerHTML = '';

    const vuelosFiltrados = vuelos.filter(function (vuelo) {
        const datosVuelo = (vuelo.destino + ' ' + vuelo.ciudad + ' ' + vuelo.pais + ' ' + vuelo.aerolinea).toLowerCase();
        const coincideTexto = datosVuelo.includes(texto);
        const coincideContinente = continenteSeleccionado === 'todos' || vuelo.continente === continenteSeleccionado;
        const coincideEstado = estado === 'todos' || vuelo.estado === estado;
        const coincideFechaDesde = fechaDesde === '' || vuelo.fecha >= fechaDesde;
        const coincideFechaHasta = fechaHasta === '' || vuelo.fecha <= fechaHasta;

        return coincideTexto && coincideContinente && coincideEstado && coincideFechaDesde && coincideFechaHasta;
    });

    // añado a la galeria solo los vuelos que han pasado los filtros
    vuelosFiltrados.forEach(function (vuelo) {
        galeria.appendChild(crearTarjeta(vuelo));
    });

    // muestro el mensaje adecuado según haya vuelos o no
    historialVacio.style.display = vuelos.length === 0 ? 'block' : 'none';
    mensajeSinResultados.style.display = vuelos.length > 0 && vuelosFiltrados.length === 0 ? 'block' : 'none';
    contador.textContent = 'Mostrando ' + vuelosFiltrados.length + ' de ' + vuelos.length + ' vuelos de ejemplo.';
}

// preparo la ventana emergente con los datos del vuelo
function mostrarDetalles(vuelo) {
    const detalles = document.getElementById('flightDetails');
    detalles.innerHTML = '';

    const datos = [
        ['Ruta', vuelo.origen + ' → ' + vuelo.destino],
        ['Destino', vuelo.ciudad + ', ' + vuelo.pais],
        ['Fecha', mostrarFecha(vuelo.fecha)],
        ['Aerolínea', vuelo.aerolinea],
        ['Estado', vuelo.estado],
        ['Precio', vuelo.precio + ' €']
    ];

    datos.forEach(function (dato) {
        const fila = document.createElement('p');
        fila.textContent = dato[0] + ': ' + dato[1];
        detalles.appendChild(fila);
    });

    const ventana = new bootstrap.Modal(document.getElementById('flightDetailsModal'));
    ventana.show();
}

// creo un archivo de texto con el itinerario
function descargarItinerario(vuelo) {
    const texto = 'Vuelo: ' + vuelo.origen + ' → ' + vuelo.destino
        + '\nDestino: ' + vuelo.ciudad + ', ' + vuelo.pais
        + '\nFecha: ' + mostrarFecha(vuelo.fecha)
        + '\nAerolínea: ' + vuelo.aerolinea
        + '\nEstado: ' + vuelo.estado
        + '\nPrecio: ' + vuelo.precio + ' €';

    const archivo = new Blob([texto], { type: 'text/plain' });
    const enlace = document.createElement('a');
    enlace.href = URL.createObjectURL(archivo);
    enlace.download = 'itinerario-' + vuelo.destino + '.txt';
    enlace.click();
    URL.revokeObjectURL(enlace.href);
}

// cambio el estado a cancelado y actualizo el historial
function cancelarVuelo(id) {
    const seguro = confirm('¿Quieres cancelar este vuelo de ejemplo?');

    if (!seguro) {
        return;
    }

    vuelos.forEach(function (vuelo) {
        if (vuelo.id === id) {
            vuelo.estado = 'cancelado';
        }
    });

    guardarVuelos();
    mostrarVuelos();
}

// conecto los botones y campos de la pagina con sus funciones
function iniciarHistorial() {
    vuelos = cargarVuelos();

    document.getElementById('searchInput').addEventListener('input', mostrarVuelos);
    document.getElementById('fechaDesde').addEventListener('change', mostrarVuelos);
    document.getElementById('fechaHasta').addEventListener('change', mostrarVuelos);
    document.getElementById('filtroEstado').addEventListener('change', mostrarVuelos);

    // al pulsar una region, actualizo el filtro y el aspecto del boton
    document.querySelectorAll('.filter-btn').forEach(function (boton) {
        boton.addEventListener('click', function () {
            continenteSeleccionado = boton.dataset.filter;

            document.querySelectorAll('.filter-btn').forEach(function (otroBoton) {
                otroBoton.classList.remove('btn-dark');
                otroBoton.classList.add('btn-outline-secondary');
            });

            boton.classList.remove('btn-outline-secondary');
            boton.classList.add('btn-dark');
            mostrarVuelos();
        });
    });

    // este boton deja todos los filtros como estaban al principio
    document.getElementById('limpiarFiltros').addEventListener('click', function () {
        document.getElementById('searchInput').value = '';
        document.getElementById('fechaDesde').value = '';
        document.getElementById('fechaHasta').value = '';
        document.getElementById('filtroEstado').value = 'todos';
        continenteSeleccionado = 'todos';

        document.querySelectorAll('.filter-btn').forEach(function (boton) {
            const seleccionado = boton.dataset.filter === 'todos';
            boton.classList.toggle('btn-dark', seleccionado);
            boton.classList.toggle('btn-outline-secondary', !seleccionado);
        });

        mostrarVuelos();
    });



    
    // enseño los vuelos cuando se abre la pagina
    mostrarVuelos();
}

// espero a que cargue el html para empezar
document.addEventListener('DOMContentLoaded', iniciarHistorial);