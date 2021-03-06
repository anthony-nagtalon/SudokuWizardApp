package com.example.sudokuWizard.view

import kotlinx.android.synthetic.main.activity_sudoku_game.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sudokuWizard.R
import com.example.sudokuWizard.engine.Cell
import com.example.sudokuWizard.view.customview.BoardView
import com.example.sudokuWizard.viewmodel.BoardViewModel

class SudokuGameActivity : AppCompatActivity(), BoardView.OnTouchListener {

    private lateinit var viewModel : BoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku_game)

        board_view.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(BoardViewModel::class.java)
        viewModel.sudokuGame
            .selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it)})
        viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it) })

        val buttons = listOf(one_button, two_button, three_button,
            four_button, five_button, six_button, seven_button,
            eight_button, nine_button)

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.sudokuGame.handleInput(index + 1)
            }
        }

        clear_button.setOnClickListener {
            viewModel.sudokuGame.handleInput(0)
        }

        solve_button.setOnClickListener {
            if(!viewModel.sudokuGame.solve()) {
                Toast.makeText(this, "Error: Board could not be solved.", Toast.LENGTH_SHORT).show()
            }
        }

        edit_button.setOnClickListener {
            viewModel.sudokuGame.enableBoardEdit()
        }

        clear_board_button.setOnClickListener {
            viewModel.sudokuGame.clear()
        }
    }

    private fun updateCells(cells : Array<Array<Cell>>?) = cells?.let {
        board_view.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell : Pair<Int, Int>?) = cell?.let {
        board_view.updateSelectedCellUI(cell.first, cell.second)
    }

    override fun onCellTouched(row : Int, col : Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}