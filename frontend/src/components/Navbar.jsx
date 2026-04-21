import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { user, signOut } = useAuth()
  const navigate = useNavigate()

  function handleLogout() {
    signOut()
    navigate('/login')
  }

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <span className="icon">🚗</span>
        MisAutos
      </div>
      <div className="navbar-right">
        <span className="navbar-user">Hola, {user?.name}</span>
        <button onClick={handleLogout} className="btn-logout">Cerrar sesión</button>
      </div>
    </nav>
  )
}
