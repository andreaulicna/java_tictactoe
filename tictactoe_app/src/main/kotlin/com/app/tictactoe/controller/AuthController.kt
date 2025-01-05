package com.app.tictactoe.controller

import com.app.tictactoe.model.User
import com.app.tictactoe.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class AuthController @Autowired constructor(
    private val userService: UserService
) {

    @GetMapping("/")
    fun dashboard(model: Model): String {
        val user = getCurrentUser()
        model.addAttribute("user", user)
        return "dashboard"
    }

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        model.addAttribute("user", User())
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@ModelAttribute user: User, model: Model): String {
        return try {
            userService.registerUser(user.username, user.password, user.email)
            "redirect:/login"
        } catch (e: IllegalArgumentException) {
            model.addAttribute("error", e.message)
            "register"
        }
    }

    @GetMapping("/login")
    fun login(
        @RequestParam(value = "error", required = false) error: String?,
        model: Model
    ): String {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.")
        }
        return "login"
    }

    @PostMapping("/logout")
    fun logout(): String {
        return "redirect:/login?logout"
    }

    @GetMapping("/profile")
    fun showProfilePage(model: Model): String {
        val user = getCurrentUser()
        model.addAttribute("user", user)
        return "profile"
    }

    @PostMapping("/profile")
    fun updateUsername(
        @RequestParam username: String,
        model: Model
    ): String {
        val user = getCurrentUser()
        return try {
            userService.updateUsername(user.id, username)
            updateAuthentication(username)
            model.addAttribute("success", "Username updated successfully")
            model.addAttribute("user", userService.findById(user.id))
            "profile"
        } catch (e: IllegalArgumentException) {
            model.addAttribute("error", e.message)
            model.addAttribute("user", userService.findById(user.id))
            "profile"
        }
    }

    private fun getCurrentUser(): User {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name
        return userService.findByUsername(username)
    }

    private fun updateAuthentication(newUsername: String) {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val newAuth = UsernamePasswordAuthenticationToken(
            newUsername, authentication.credentials, authentication.authorities
        )
        SecurityContextHolder.getContext().setAuthentication(newAuth)
    }
}
