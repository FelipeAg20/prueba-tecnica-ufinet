// Maps common Spanish color names to CSS colors for the dot indicator
const COLOR_MAP = {
  blanco: '#ffffff', negro: '#111827', rojo: '#ef4444', azul: '#3b82f6',
  verde: '#22c55e', amarillo: '#eab308', gris: '#6b7280', plata: '#cbd5e1',
  plateado: '#cbd5e1', naranja: '#f97316', morado: '#a855f7', cafe: '#92400e',
  beige: '#d4b483', rosa: '#ec4899',
}

function getColorDot(colorName) {
  const key = colorName?.toLowerCase().trim()
  return COLOR_MAP[key] || '#94a3b8'
}

export default function CarCard({ car, onEdit, onDelete }) {
  return (
    <div className="car-card">
      <div className="car-card-accent" />
      <div className="car-card-icon">🚙</div>
      <div className="car-card-plate">{car.plateNumber}</div>
      <p className="car-card-name">{car.brand} {car.model}</p>
      <div className="car-card-meta">
        <span>📅 {car.year}</span>
        <span>
          <span
            className="car-color-dot"
            style={{ '--dot-color': getColorDot(car.color) }}
          />
          {car.color}
        </span>
      </div>
      <div className="car-card-actions">
        <button className="btn-edit" onClick={() => onEdit(car)}>✏️ Editar</button>
        <button className="btn-delete" onClick={() => onDelete(car.id)}>🗑️ Eliminar</button>
      </div>
    </div>
  )
}
