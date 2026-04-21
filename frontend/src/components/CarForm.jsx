import { useState, useEffect } from 'react'

const EMPTY_FORM = { brand: '', model: '', year: '', plateNumber: '', color: '' }

export default function CarForm({ onSubmit, initial, onCancel }) {
  const [form, setForm] = useState(EMPTY_FORM)

  useEffect(() => {
    setForm(initial ? { ...initial, year: String(initial.year) } : EMPTY_FORM)
  }, [initial])

  function handleChange(e) {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  function handleSubmit(e) {
    e.preventDefault()
    onSubmit({ ...form, year: parseInt(form.year, 10) })
  }

  return (
    <form onSubmit={handleSubmit} className="car-form">
      <div className="form-row">
        <div className="form-field">
          <label>Marca</label>
          <input name="brand" value={form.brand} onChange={handleChange} placeholder="Toyota" required />
        </div>
        <div className="form-field">
          <label>Modelo</label>
          <input name="model" value={form.model} onChange={handleChange} placeholder="Corolla" required />
        </div>
      </div>

      <div className="form-row">
        <div className="form-field">
          <label>Año</label>
          <input
            name="year"
            type="number"
            value={form.year}
            onChange={handleChange}
            placeholder="2024"
            min="1900"
            max="2100"
            required
          />
        </div>
        <div className="form-field">
          <label>Placa</label>
          <input name="plateNumber" value={form.plateNumber} onChange={handleChange} placeholder="ABC-123" required />
        </div>
      </div>

      <div className="form-field">
        <label>Color</label>
        <input name="color" value={form.color} onChange={handleChange} placeholder="Blanco" required />
      </div>

      <div className="form-actions">
        <button type="submit" className="btn-submit">
          {initial ? 'Guardar cambios' : 'Crear auto'}
        </button>
        {onCancel && (
          <button type="button" className="btn-cancel" onClick={onCancel}>
            Cancelar
          </button>
        )}
      </div>
    </form>
  )
}
