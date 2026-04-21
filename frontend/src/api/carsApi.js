import api from './axiosInstance'

export const getCars = () => api.get('/cars')

export const searchCars = (params) => api.get('/cars/search', { params })

export const createCar = (data) => api.post('/cars', data)

export const updateCar = (id, data) => api.put(`/cars/${id}`, data)

export const deleteCar = (id) => api.delete(`/cars/${id}`)
