import { useState, useEffect } from 'react'
import { getCars, searchCars, createCar, updateCar, deleteCar } from '../api/carsApi'
import CarCard from '../components/CarCard'
import CarForm from '../components/CarForm'
import Navbar from '../components/Navbar'

const EMPTY_SEARCH = { plate: '', model: '', brand: '', year: '' }

export default function CarsPage() {
  const [cars, setCars] = useState([])
  const [editingCar, setEditingCar] = useState(null)
  const [showForm, setShowForm] = useState(false)
  const [error, setError] = useState('')
  const [search, setSearch] = useState(EMPTY_SEARCH)
  const [isSearchActive, setIsSearchActive] = useState(false)

  useEffect(() => {
    loadCars()
  }, [])

  async function loadCars() {
    try {
      const { data } = await getCars()
      setCars(data)
      setIsSearchActive(false)
    } catch {
      setError('No se pudieron cargar los autos')
    }
  }

  async function handleSearch(e) {
    e.preventDefault()
    const params = Object.fromEntries(
      Object.entries(search).filter(([, v]) => v !== '')
    )
    try {
      const { data } = await searchCars(params)
      setCars(data)
      setIsSearchActive(true)
      setError('')
    } catch {
      setError('Error al realizar la búsqueda')
    }
  }

  function handleSearchChange(e) {
    setSearch((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  async function handleClearSearch() {
    setSearch(EMPTY_SEARCH)
    await loadCars()
  }

  async function handleCreate(formData) {
    try {
      const { data } = await createCar(formData)
      setCars((prev) => [...prev, data])
      setShowForm(false)
      setError('')
    } catch (err) {
      setError(err.response?.data?.error || 'Error al crear el auto')
    }
  }

  async function handleUpdate(formData) {
    try {
      const { data } = await updateCar(editingCar.id, formData)
      setCars((prev) => prev.map((c) => (c.id === data.id ? data : c)))
      setEditingCar(null)
      setError('')
    } catch (err) {
      setError(err.response?.data?.error || 'Error al actualizar el auto')
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('¿Eliminar este auto?')) return
    try {
      await deleteCar(id)
      setCars((prev) => prev.filter((c) => c.id !== id))
    } catch {
      setError('Error al eliminar el auto')
    }
  }

  function handleEdit(car) {
    setEditingCar(car)
    setShowForm(false)
  }

  function handleCloseModal() {
    setEditingCar(null)
    setShowForm(false)
  }

  const isModalOpen = showForm || !!editingCar

  return (
    <div>
      <Navbar />

      <div className="page-container">
        <div className="page-header">
          <h2>Mis autos {isSearchActive && `(${cars.length} resultado${cars.length !== 1 ? 's' : ''})`}</h2>
          <button className="btn-add" onClick={() => { setShowForm(true); setEditingCar(null) }}>
            + Agregar auto
          </button>
        </div>

        {/* Search / filter bar */}
        <form className="search-bar" onSubmit={handleSearch}>
          <input
            name="plate"
            value={search.plate}
            onChange={handleSearchChange}
            placeholder="Buscar por placa..."
          />
          <input
            name="model"
            value={search.model}
            onChange={handleSearchChange}
            placeholder="Buscar por modelo..."
          />
          <input
            name="brand"
            value={search.brand}
            onChange={handleSearchChange}
            placeholder="Filtrar por marca..."
          />
          <input
            name="year"
            type="number"
            value={search.year}
            onChange={handleSearchChange}
            placeholder="Año"
            min="1900"
            max="2100"
          />
          <button type="submit" className="btn-search">Buscar</button>
          {isSearchActive && (
            <button type="button" className="btn-clear" onClick={handleClearSearch}>
              Limpiar
            </button>
          )}
        </form>

        {error && <div className="error-msg">{error}</div>}

        {cars.length === 0 ? (
          <div className="empty-state">
            <div className="empty-icon">🚗</div>
            <p>{isSearchActive ? 'No se encontraron autos con esos filtros.' : 'No tienes autos registrados todavía.'}</p>
          </div>
        ) : (
          <div className="cars-grid">
            {cars.map((car) => (
              <CarCard key={car.id} car={car} onEdit={handleEdit} onDelete={handleDelete} />
            ))}
          </div>
        )}
      </div>

      {/* Modal for create / edit */}
      {isModalOpen && (
        <div className="modal-overlay" onClick={(e) => e.target === e.currentTarget && handleCloseModal()}>
          <div className="modal-card">
            <h3>{editingCar ? 'Editar auto' : 'Agregar auto'}</h3>
            <CarForm
              onSubmit={editingCar ? handleUpdate : handleCreate}
              initial={editingCar}
              onCancel={handleCloseModal}
            />
          </div>
        </div>
      )}
    </div>
  )
}
