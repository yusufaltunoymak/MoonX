import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moonx.databinding.FragmentMeditationHomeBinding
import com.example.moonx.ui.adapter.MeditationAdapter
import com.example.moonx.ui.adapter.MeditationAdapter2

class MeditationHomeFragment : Fragment() {

    private lateinit var binding: FragmentMeditationHomeBinding
    private lateinit var adapter1: MeditationAdapter
    private lateinit var adapter2: MeditationAdapter2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeditationHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter1 = MeditationAdapter()
        adapter2 = MeditationAdapter2()

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter1

        binding.recyclerView2.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView2.adapter = adapter2
    }
}
