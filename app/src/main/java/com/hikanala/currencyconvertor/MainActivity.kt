package com.hikanala.currencyconvertor


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hikanala.currencyconvertor.databinding.ActivityMainBinding
import com.hikanala.currencyconvertor.abouts_us.AboutUsActivity
import com.hikanala.currencyconvertor.adapter.CountryAdapter
import com.hikanala.currencyconvertor.data.Currency
import com.hikanala.currencyconvertor.settings.SettingActivity
import com.hikanala.currencyconvertor.utils.Converter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var selectedCurrency = 0.0031
    private var selectedTargetCurrency = 0.0031

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.llAmountSelection.setOnClickListener{
            showCountryList(0)
        }

        binding.llConvertedSelection.setOnClickListener{
            showCountryList(1)
        }

        binding.btnConvert.setOnClickListener {

            if (binding.etAmount.text.isNullOrEmpty()){
                binding.etAmount.setBackgroundResource(R.drawable.edite_text_back_error)
                binding.tvAmountError.visibility = View.VISIBLE
            }
            else {
                binding.etAmount.setBackgroundResource(R.drawable.edite_text_back)
                binding.tvAmountError.visibility = View.INVISIBLE
                var convertedAmount = Converter.convert(
                    binding.etAmount.text.toString().toInt(),
                    selectedCurrency,
                    selectedTargetCurrency
                )
                binding.tvConvertedAmount.text = convertedAmount.toString()
            }
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mn_about_us -> {
                    var intent = Intent(this, AboutUsActivity::class.java)
                    startActivity(intent)
                }
                R.id.mn_setting -> {
                    var intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun showCountryList(type: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
        val recyclerView: RecyclerView = dialogView.findViewById(R.id.recyclerView)
        val closeButton: Button = dialogView.findViewById(R.id.closeButton)

        val lkr = Currency("LKR", R.drawable.lk, 0.0031)
        val usd = Currency("USD", R.drawable.us, 1.0)
        val chn = Currency("Yuan", R.drawable.cn, 0.139637)
        val inr = Currency("INR", R.drawable.`in`, 0.012032)
        //val yen = Currency("Yen", R.drawable.jp, 147.734)
        val dataList = listOf(lkr, usd, chn, inr)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val adapter = CountryAdapter(dataList) { selectedItem ->
            if (type == 0) {
                binding.tvAmountCountry.text = selectedItem.currency
                binding.ivAmountFlag.setBackgroundResource(selectedItem.flag)
                selectedCurrency = selectedItem.value
            }
            else{
                binding.tvConvertedCountry.text = selectedItem.currency
                binding.ivConvertedFlag.setBackgroundResource(selectedItem.flag)
                selectedTargetCurrency = selectedItem.value
            }
            dialogBuilder.dismiss()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        closeButton.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }
}