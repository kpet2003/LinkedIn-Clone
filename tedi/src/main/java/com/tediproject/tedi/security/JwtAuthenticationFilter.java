// package com.tediproject.tedi.security;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;

// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.web.filter.OncePerRequestFilter;


// import com.tediproject.tedi.model.User;
// import com.tediproject.tedi.repo.UserRepo;
// import com.tediproject.tedi.service.UserService;

// import java.io.IOException;



// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private UserDetailsService userDetailsService;


//     @Autowired
//     private UserRepo userRepo;

//     @Autowired
//     private JwtUtil jwtUtil;


//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         final String authorizationHeader = request.getHeader("Authorization");

//         String email = null;
//         String jwtToken = null;

//         if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//             jwtToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
//             try {
//                 email = jwtUtil.extractEmail(jwtToken); // Extract email from the token
//             } catch (Exception e) {
//                 // Handle token extraction/validation errors
//                 System.out.println("Error extracting email from token: " + e.getMessage());
//             }
//         }

//         if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//             User userDetails = userRepo.findByEmail(email); // Load user details

//             if (jwtUtil.validateToken(jwtToken, userDetails)) {
//                 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                     userDetails, null, userDetails.getAuthorities()); // Create authentication token

//                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//                 SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Set authentication in context
//             }
//         }

//         filterChain.doFilter(request, response); // Continue the filter chain
//     }
// }
