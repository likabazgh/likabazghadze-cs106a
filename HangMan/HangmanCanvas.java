
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	private HangmanCanvas canvas;
	GLabel label;
	GLabel letter;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		drawBeam(); //beam should already be drawn
	}

	public void startPlaying(int n) {
		hangProcess(n);
	}

	private void hangProcess(int n) {
		switch (n) {
		case 1:
			drawHead();
			break;
		case 2:
			drawBody();
			break;
		case 3:
			drawLeftArm();
			break;
		case 4:
			drawRightArm();
			break;
		case 5:
			drawLeftLeg();
			break;
		case 6:
			drawRightLeg();
			break;
		case 7:
			drawLeftFoot();
			break;
		case 8:
			drawRightFoot();
			break;
		}
	}

	private void drawBeam() {
		int scaffoldstartingX = this.getWidth() / 2 - BEAM_LENGTH;
		int scaffoldstartingY = Yoffset + SCAFFOLD_HEIGHT;
		int scaffoldendingX = this.getWidth() / 2 - BEAM_LENGTH;
		int scaffoldendingY = Yoffset;

		GLine scaffold = new GLine(scaffoldstartingX, scaffoldstartingY, scaffoldendingX, scaffoldendingY);
		scaffold.setColor(Color.GREEN);
		add(scaffold);

		int beamendingX = this.getWidth() / 2;
		int beamendingY = Yoffset;

		GLine beam = new GLine(scaffoldendingX, scaffoldendingY, beamendingX, beamendingY);
		beam.setColor(Color.GREEN);
		add(beam);

		int ropeendingX = this.getWidth() / 2;
		int ropeendingY = Yoffset + ROPE_LENGTH;

		GLine rope = new GLine(beamendingX, beamendingY, ropeendingX, ropeendingY);
		rope.setColor(Color.GREEN);
		add(rope);
	}

	private void drawHead() {
		int headX = this.getWidth() / 2 - HEAD_RADIUS;
		int headY = Yoffset + ROPE_LENGTH;

		GRect head = new GRect(headX, headY, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		head.setColor(Color.GREEN);
		add(head);
		
		GLine hair1=new GLine(this.getWidth() / 2-10, Yoffset + 6, this.getWidth() / 2, Yoffset + ROPE_LENGTH);
		hair1.setColor(Color.GREEN);
		add(hair1);
		
		GLine hair2=new GLine(this.getWidth() / 2+10, Yoffset + 6, this.getWidth() / 2, Yoffset + ROPE_LENGTH);
		hair2.setColor(Color.GREEN);
		add(hair2);
		
		GOval eye1=new GOval(this.getWidth() / 2-15, Yoffset + ROPE_LENGTH + 15, 5, 5);
		eye1.setColor(Color.GREEN);
		add(eye1);
		
		GOval eye2=new GOval(this.getWidth() / 2+10, Yoffset + ROPE_LENGTH + 15, 5, 5);
		eye2.setColor(Color.GREEN);
		add(eye2);

		GLine mouth= new GLine(this.getWidth() / 2-10, Yoffset + ROPE_LENGTH+ 32, this.getWidth() / 2+10, Yoffset + ROPE_LENGTH+ 32);
		mouth.setColor(Color.GREEN);
		add(mouth);
		
		GRect tongue= new GRect(this.getWidth() / 2+4, Yoffset + ROPE_LENGTH+ 32, 3, 5);
		tongue.setColor(Color.GREEN);
		add(tongue);
	}

	private void drawBody() {
		
		int neck1startingX = this.getWidth() / 2-15;
		int neck1startingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS;
		int neck1endingX = this.getWidth() / 2-15;
		int neck1endingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS+30 ;

		GLine neck1 = new GLine(neck1startingX, neck1startingY, neck1endingX, neck1endingY);
		neck1.setColor(Color.GREEN);
		add(neck1);
		
		int neck2startingX = this.getWidth() / 2+15;
		int neck2startingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS;
		int neck2endingX = this.getWidth() / 2+15;
		int neck2endingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS+30 ;
		
		GLine neck2 = new GLine(neck2startingX, neck2startingY, neck2endingX, neck2endingY);
		neck2.setColor(Color.GREEN);
		add(neck2);
		
		int bodyStartingX=this.getWidth() / 2-BODY_WIDTH/2;
		int bodyStartingY=Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS+NECK_LENGTH;
		
		GRect body=new GRect(bodyStartingX, bodyStartingY, BODY_WIDTH, BODY_LENGTH);
		body.setColor(Color.GREEN);
		add(body);
		
		GRect belt=new GRect(bodyStartingX, Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + NECK_LENGTH + BODY_LENGTH/2,  BODY_WIDTH, 20);
		belt.setColor(Color.GREEN);
		add(belt);
		
		GOval ring=new GOval(this.getWidth() / 2-10, Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + NECK_LENGTH + BODY_LENGTH/2, 20, 20);
		ring.setColor(Color.GREEN);
		add(ring);
		
		GLine line=new GLine(this.getWidth() / 2, Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + NECK_LENGTH+BODY_LENGTH/2+20, this.getWidth() / 2,Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + NECK_LENGTH+BODY_LENGTH);
		line.setColor(Color.GREEN);
		add(line);
	}

	private void drawLeftArm() {
		int leftUpperArmstartingX = this.getWidth() / 2-BODY_WIDTH/2;
		int leftUpperArmstartingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD+20;
		int leftUpperArmendingX = this.getWidth() / 2 -BODY_WIDTH/2- UPPER_ARM_LENGTH;
		int leftUpperArmendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD+20;

		GLine leftUpperArm = new GLine(leftUpperArmstartingX, leftUpperArmstartingY, leftUpperArmendingX,
				leftUpperArmendingY);
		leftUpperArm.setColor(Color.GREEN);
		add(leftUpperArm);

		int leftLowerArmendingX = this.getWidth() / 2 -BODY_WIDTH/2- UPPER_ARM_LENGTH;
		int leftLowerArmendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD+ 20+LOWER_ARM_LENGTH;

		GLine leftLowerArm = new GLine(leftUpperArmendingX, leftUpperArmendingY, leftLowerArmendingX,
				leftLowerArmendingY);
		leftLowerArm.setColor(Color.GREEN);
		add(leftLowerArm);
	}

	private void drawRightArm() {
		int rightUpperArmstartingX = this.getWidth() / 2+BODY_WIDTH/2;
		int rightUpperArmstartingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD+20;
		int rightUpperArmendingX = this.getWidth() / 2 +BODY_WIDTH/2+ UPPER_ARM_LENGTH;
		int rightUpperArmendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD+20;

		GLine rightUpperArm = new GLine(rightUpperArmstartingX, rightUpperArmstartingY, rightUpperArmendingX,
				rightUpperArmendingY);
		rightUpperArm.setColor(Color.GREEN);
		add(rightUpperArm);

		int rightLowerArmendingX = this.getWidth() / 2 +BODY_WIDTH/2+ UPPER_ARM_LENGTH;
		int rightLowerArmendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD +20+ LOWER_ARM_LENGTH;

		GLine rightLowerArm = new GLine(rightUpperArmendingX, rightUpperArmendingY, rightLowerArmendingX,
				rightLowerArmendingY);
		rightLowerArm.setColor(Color.GREEN);
		add(rightLowerArm);
	}

	private void drawLeftLeg() {
		int leftLegstartingX = this.getWidth() / 2 - 20;
		int leftLegstartingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + NECK_LENGTH + BODY_LENGTH;
		int leftLegendingX = this.getWidth() / 2 - 20;
		int leftLegendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + NECK_LENGTH + LEG_LENGTH;

		GLine leftLeg = new GLine(leftLegstartingX, leftLegstartingY, leftLegendingX, leftLegendingY);
		leftLeg.setColor(Color.GREEN);
		add(leftLeg);
	}

	private void drawRightLeg() {
		int rightLegstartingX = this.getWidth() / 2 + 20;
		int rightLegstartingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + NECK_LENGTH + BODY_LENGTH;
		int rightLegendingX = this.getWidth() / 2 + 20;
		int rightLegendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + NECK_LENGTH + LEG_LENGTH;

		GLine rightLeg = new GLine(rightLegstartingX, rightLegstartingY, rightLegendingX, rightLegendingY);
		rightLeg.setColor(Color.GREEN);
		add(rightLeg);
	}

	private void drawLeftFoot() {
		int leftFootstartingX = this.getWidth() / 2 - 20;
		int leftFootstartingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + NECK_LENGTH + LEG_LENGTH;
		int leftFootendingX = this.getWidth() / 2 - 20 - FOOT_LENGTH;
		int leftFootendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + NECK_LENGTH + LEG_LENGTH;

		GLine leftFoot = new GLine(leftFootstartingX, leftFootstartingY, leftFootendingX, leftFootendingY);
		leftFoot.setColor(Color.GREEN);
		add(leftFoot);
	}

	private void drawRightFoot() {
		int rightFootstartingX = this.getWidth() / 2 + 20;
		int rightFootstartingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + NECK_LENGTH + LEG_LENGTH;
		int rightFootendingX = this.getWidth() / 2 + 20 + FOOT_LENGTH;
		int rightFootendingY = Yoffset + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + NECK_LENGTH + LEG_LENGTH;

		GLine rightFoot = new GLine(rightFootstartingX, rightFootstartingY, rightFootendingX, rightFootendingY);
		rightFoot.setColor(Color.GREEN);
		add(rightFoot);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String SecretWord) {
		if (label != null) {
			remove(label);
		}
		label = new GLabel(SecretWord, 50, 400);
		label.setColor(Color.GREEN);
		label.setFont("-30");
		add(label);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	String strg = new String(" ");

	public void noteIncorrectGuess(char s) {

		if (strg.indexOf(s) == -1) {
			GLabel incorrect = new GLabel(strg + " " + s);
			incorrect.setColor(Color.RED);

			if (incorrect != null) {
				remove(incorrect);
			}
			add(incorrect, 50, 420);
			strg += " " + s;
		}

	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 300;
	private static final int BEAM_LENGTH = 130;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 25;
	private static final int BODY_LENGTH = 120;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 40;
	private static final int LOWER_ARM_LENGTH = 40;
	private static final int HIP_WIDTH = 50;
	private static final int LEG_LENGTH = 40;
	private static final int FOOT_LENGTH = 30;

	private static final int Yoffset = 50;
	private static final int NECK_LENGTH=30;
	private static final int BODY_WIDTH=100;

}
