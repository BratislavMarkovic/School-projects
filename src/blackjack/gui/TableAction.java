package blackjack.gui;

import java.awt.event.ActionListener;

import blackjack.Table;

public abstract class TableAction implements ActionListener {

	protected Table table;
	protected int position;
	
	public TableAction(Table table, int position){
		this.table = table;
		this.position = position;
	}

}

