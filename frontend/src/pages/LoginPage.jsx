import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { login } from '../api/authApi'
import { useAuth } from '../context/AuthContext'

export default function LoginPage() {
  const [form, setForm] = useState({ email: '', password: '' })
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
      const { data } = await login(form)
      signIn(data.token, { name: data.name, email: data.email })
      navigate('/cars')
    } catch (err) {
      setError(err.response?.data?.error || 'Credenciales incorrectas')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-logo">🚗</div>
        <h2>Bienvenido</h2>
        <p className="auth-subtitle">Ingresa a tu cuenta para gestionar tus autos</p>

        {error && <div className="error-msg">{error}</div>}

        <form onSubmit={handleSubmit}>
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
              placeholder="••••••"
              required
            />
          </div>

          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Ingresando...' : 'Iniciar sesión'}
          </button>
        </form>

        <p className="auth-link">
          ¿No tienes cuenta? <Link to="/register">Regístrate aquí</Link>
        </p>
      </div>
    </div>
  )
}
