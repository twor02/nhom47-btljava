package com.now2048.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


import com.now2048.game.DrawUtils;
import com.now2048.game.Game;
import com.now2048.game.GameBoard;
import com.now2048.game.ScoreManager;

public class PlayPanel extends GuiPanel {

	private GameBoard board;
	private BufferedImage info;
	private ScoreManager scores;
	private Font scoreFont;

	// Game Over
	private GuiButton tryAgain;
	private GuiButton mainMenu;
	private int spacing = 20;
	private int ButtonWidth = 160 * 2 + spacing;
	private int buttonHeight = 50;
	private boolean added;
	private int alpha = 0;
	private Font gameOverFont;
	
	public PlayPanel() {
		scoreFont = Game.main.deriveFont(24f);
		gameOverFont = Game.main.deriveFont(70f);
		board = new GameBoard(Game.WIDTH / 2 - GameBoard.BOARD_WIDTH / 2, Game.HEIGHT - GameBoard.BOARD_HEIGHT - 20);
		scores = board.getScores();
		info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

		mainMenu = new GuiButton(Game.WIDTH / 2 - ButtonWidth / 2, 450, ButtonWidth, buttonHeight);
		tryAgain = new GuiButton(mainMenu.getX(), mainMenu.getY() - spacing - buttonHeight, ButtonWidth, buttonHeight);

		tryAgain.setText("Try Again");
		mainMenu.setText("Back to Main Menu");

		tryAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.getScores().reset();
				board.reset();
				alpha = 0;

				remove(tryAgain);
				remove(mainMenu);

				added = false;
			}
		});


		mainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Menu");
			}
		});
	}

	private void drawGui(Graphics2D g) {
		// Draw it
		Graphics2D g2d = (Graphics2D) info.getGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
		g2d.setColor(Color.lightGray);
		g2d.setFont(scoreFont);
		g2d.drawString("" + scores.getCurrentScore(), 30, 40);
		g2d.setColor(Color.red);
		g2d.drawString("Best: " + scores.getCurrentTopScore(), Game.WIDTH - DrawUtils.getMessageWidth("Best: " + scores.getCurrentTopScore(), scoreFont, g2d) - 20, 40);
		g2d.dispose();
		g.drawImage(info, 0, 0, null);
	}

	public void drawGameOver(Graphics2D g) {
		g.setColor(new Color(222, 222, 222, alpha));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.red);
		g.drawString("Game Over!", Game.WIDTH / 2 - DrawUtils.getMessageWidth("Game Over!", gameOverFont, g) / 2, 250);
	}

	@Override
	public void update() {
		board.update();
		if (board.isDead()) {
			alpha++;
			if (alpha > 170) alpha = 170;
		}
	}

	@Override
	public void render(Graphics2D g) {
		drawGui(g);
		board.render(g);
		
		if (board.isDead()) {
			if (!added) {
				added = true;
				add(mainMenu);
				add(tryAgain);
			}
			drawGameOver(g);
		}
		super.render(g);
	}
}