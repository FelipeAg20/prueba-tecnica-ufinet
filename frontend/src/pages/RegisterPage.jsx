import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { register } from '../api/authApi'
import { useAuth } from '../context/AuthContext'

export default function RegisterPage() {
  const [form, setForm] = useState({ name: '', email: '', password: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const { signIn } = useAuth()
  const navigate = useNavigate()

  function handleChange(e) {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const { data } = await register(form)
      signIn(data.token, { name: data.name, email: data.email })
      navigate('/cars')
    } catch (err) {
      setError(err.response?.data?.error || 'Error al registrarse')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-logo">🚗</div>
        <h2>Crear cuenta</h2>
        <p className="auth-subtitle">Registra tu cuenta para empezar a gestionar tus autos</p>

        {error && <div className="error-msg">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-field">
            <label>Nombre completo</label>
            <input
              name="name"
              type="text"
              value={form.name}
              onChange={handleChange}
              placeholder="Carlos Ruiz"
              required
            />
          </div>
          <div className="form-field">
            <label>Correo electrónico</label>
            <input
              name="email"
              type="email"
              value={form.email}
              onChange={handleChange}
              placeholder="tu@correo.com"
              required
            />
          </div>
          <div className="form-field">
            <label>Contraseña</label>
            <input
              name="password"
              type="password"
              value={form.password}
              onChange={handleChange}
              placeholder="Mínimo 6 caracteres"
              required
              minLength={6}
            />
          </div>

          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Creando cuenta...' : 'Registrarse'}
          </button>
        </form>

        <p className="auth-link">
          ¿Ya tienes cuenta? <Link to="/login">Inicia sesión</Link>
        </p>
      </div>
    </div>
  )
}
