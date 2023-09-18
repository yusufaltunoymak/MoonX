import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moonx.R
import com.example.moonx.databinding.FragmentMeditationHomeBinding
import com.example.moonx.ui.adapter.MeditationAdapter
import com.example.moonx.viewmodel.MeditationViewModel

class MeditationHomeFragment : Fragment() {

    private var _binding: FragmentMeditationHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MeditationAdapter
    private val viewModel: MeditationViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMeditationHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter1 = MeditationAdapter { clickedItem ->
            viewModel.setSong(clickedItem)
            findNavController().navigate(R.id.action_meditationFragment_to_meditationPlayerFragment)
        }

        val adapter2 = MeditationAdapter { clickedItem ->
            viewModel.setSong(clickedItem)
            findNavController().navigate(R.id.action_meditationFragment_to_meditationPlayerFragment)
        }

        binding.recyclerView.adapter = adapter1
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter1.submitList(viewModel.meditation)

        binding.recyclerView2.adapter = adapter2
        binding.recyclerView2.layoutManager = GridLayoutManager(context, 2)
        adapter2.submitList(viewModel.meditation2)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





