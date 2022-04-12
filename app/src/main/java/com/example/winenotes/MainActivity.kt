package com.example.winenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.winenotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setLayoutManager(layoutManager)

        val dividerItemDecoration = DividerItemDecoration(
            applicationContext, layoutManager.getOrientation()
        )
        binding.recyclerview.addItemDecoration(dividerItemDecoration)

        adapter = MyAdapter()
        binding.recyclerview.setAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort_by_title) {
            //function
            return true
        } else if (item.itemId == R.id.sort_by_last_modified) {
            //function
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    inner class MyViewHolder(val view: TextView) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener, View.OnLongClickListener {

            init {
              view.setOnClickListener(this)
              view.setOnLongClickListener(this)
            }

        override fun onClick(p0: View?) {
            val intent = Intent(applicationContext, WineActivity::class.java)

        }

        override fun onLongClick(p0: View?): Boolean {
            TODO("Not yet implemented")
        }

    }
    inner class MyAdapter :
        RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item__view, parent, false) as TextView
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            return 0
        }
    }
}