package com.now2048.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.now2048.game.DrawUtils;
import com.now2048.game.Game;

public class MainMenuPanel extends GuiPanel {

	private Font titleFont = Game.main.deriveFont(100f);
	private String title = "2048";
	private int buttonWidth = 220;
	
	public MainMenuPanel() {
		
		GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 280, buttonWidth, 60);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Play");
			}
		});
		playButton.setText("Play");
		add(playButton);
		
		
		GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 400, buttonWidth, 60);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitButton.setText("Quit");
		add(quitButton);
		

	}

	
	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setFont(titleFont);
		g.setColor(Color.black);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
	}
}
