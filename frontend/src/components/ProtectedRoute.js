import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

// checks whether the user is authenticated to let them access the Route
const ProtectedRoute = ({ isAuthenticated }) => {
  return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
};

export default ProtectedRoute;