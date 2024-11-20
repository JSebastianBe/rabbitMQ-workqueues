// URL del API
const apiUrl = 'http://127.0.0.1:8081/eventos/listarMongo';

async function fetchData() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error('Error al obtener datos');

        const data = await response.json();
        renderTable(data);
    } catch (error) {
        console.error('Error:', error);
    }
}

function renderTable(data) {
    const tableBody = document.getElementById('data-table').querySelector('tbody');
    tableBody.innerHTML = ''; // Limpiar tabla antes de agregar nuevos datos

    data.forEach(item => {
        const row = document.createElement('tr');

        // Crear celdas
        const cellEmpleado = document.createElement('td');
        cellEmpleado.textContent = item.empleado;

        const cellNombre = document.createElement('td');
        cellNombre.textContent = item.nombre;

        const cellFechaHora = document.createElement('td');
        cellFechaHora.textContent = item.fecha_hora;

        // Agregar celdas a la fila
        row.appendChild(cellEmpleado);
        row.appendChild(cellNombre);
        row.appendChild(cellFechaHora);

        // Agregar fila a la tabla
        tableBody.appendChild(row);
    });
}


function actualizarHora() {
    const ahora = new Date();
    const opciones = { hour: '2-digit', minute: '2-digit', second: '2-digit' };
    const horaFormateada = ahora.toLocaleTimeString('es-ES', opciones);
    document.getElementById('hora-actual').textContent = `Hora actual: ${horaFormateada}`;
}