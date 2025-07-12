package com.jfpsolucoes.unipplus2.ui.components.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

abstract class UPComponentFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content { UNIPPlus2Theme { Content() } }

    @Composable
    abstract fun Content()

    @Composable
    fun navController(): NavController {
        return requireActivity().findNavController(R.id.nav_host_main)
    }

}