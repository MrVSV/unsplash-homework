package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.unsplashhomework.databinding.FragmentDashboardBinding

/** Первое в класической клин архитектуре нет  пакета юай переименовываем в презентейшет*/

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
/** вот этот коментраий для кого оставили? удалем его */
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    /** в текущих реалиях весь код пишем в методе onViewCreated тут нужно оставить только биндинг
     * так можно избежать ошибок отображения*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /** Так как у нас одна вьюмодель на весь проект фрагмент нет смылса придумывать длиннае имя
         * viewModel обзываем в каждом фрагменте и не придумывем ничего нового
         * так же стоит винести ее глобально, так легче писать функции
         * и зачем такая реализация есть by viewModels все равно эту библиотеку подключать придется*/
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        /** зачем так делать? биндинг нужен что бы так не делать*/
        val root: View = binding.root
        /** зачем так делать? биндинг нужен что бы так не делать*/
        val textView: TextView = binding.textDashboard
        /**вот это нужно вынести в функцию и вызвать в onViewCreated */
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DashboardFragmentTest : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

   private val viewModel  by viewModels<DashboardViewModel> ()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe(){
        viewModel.text.observe(viewLifecycleOwner){
            binding.textDashboard.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}