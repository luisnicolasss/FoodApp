package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.foodapp.R
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.viewmodel.HomeViewModel


class CategoriesFragment : Fragment() {

  private lateinit var binding: FragmentCategoriesBinding
  private lateinit var categoriesAdapter: CategoriesAdapter
  private lateinit var viewModel: HomeViewModel

  companion object {
      const val CATEGORY_NAME = "com.example.foodapp.fragments.categoryName"
  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observeCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { cateogries ->
            categoriesAdapter.setCategoryList(cateogries)
        })
    }

    private fun prepareRecyclerView() {
      categoriesAdapter = CategoriesAdapter()
      binding.rvCategories.apply {
          layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
          adapter = categoriesAdapter
      }
    }


}