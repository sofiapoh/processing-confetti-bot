import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import gifAnimation.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class create_confetti extends PApplet {



GifMaker gifExport;

Circle[] circles;
Square[] squares;
Triangle[] triangles;

int c = color(random(100, 255),random(100, 255),random(100, 255));

public void setup() {
  size(400, 400);
  frameRate(50);

  gifExport = new GifMaker(this, "confetti.gif");
  gifExport.setRepeat(0);

  int numCircles, numSquares, numTriangles;
  numCircles = round(random(200, 400));
  numSquares = round(random(200, 400));
  numTriangles = round(random(200, 400));
  
  circles = new Circle[numCircles];
  for (int i = 0; i < circles.length; i++) {
    float diameter = random(3, 15);
    circles[i] = new Circle(diameter);
  }

  squares = new Square[numSquares];
  for (int i = 0; i < squares.length; i++) {
    float side = random(3, 15);
    squares[i] = new Square(side);
  }

  triangles = new Triangle[numTriangles];
  for (int i = 0; i < triangles.length; i++) {
    float side = random(3, 10);
    triangles[i] = new Triangle(side);
  }

  noStroke();
}
public void draw() {
  background(c);
  
  for (int i=0; i < circles.length; i++) {
    circles[i].move();
    circles[i].display();
  }

  for (int i=0; i < squares.length; i++) {
    squares[i].move();
    squares[i].display();
  }

  for (int i=0; i < triangles.length; i++) {
    triangles[i].move();
    triangles[i].display();
  }

   gifExport.setDelay(30);
   gifExport.addFrame();

  if (frameCount == 80) {
    gifExport.finish();
    exit();
  }
}

class Shape {
  float x, y, rotation, speed, drift;
  int direction;
  int colour;

  // Constructor
  Shape() {
    x = random(0, width);
    y = random(0, height);
    speed = random(0.2f, 2);
    colour = color(random(100, 255),random(100, 255),random(100, 255));
    rotation = random(0, TWO_PI);
    direction = PApplet.parseInt(random(0, 1));
    drift = random(-.2f, .2f);
  }

  public void move() {
    x += drift;
    float m = map(y, 0, height, 1, .75f);
    y += speed * m;
    float dir_inc = speed * .005f;
    if (direction == 0) {
      rotation -= dir_inc;
    } else {
      rotation += dir_inc;
    }
  }
  public void setColour() {
    fill(colour);
  }
}

class Circle extends Shape {
  float diameter;

  // Constructor
  Circle(float dia) {
    super();
    diameter = dia;
  }

  public void move() {
    super.move();
    if (y > (height + diameter/2)) {
      y = -diameter/2;
      x = random(0, width);
    }
  }

  public void display() {
    setColour();
    ellipse(x, y, diameter, diameter);
  }
}

class Square extends Shape {
  float side;

  // Constructor
  Square(float sd) {
    super();
    side = sd;
  }

  public void move() {
    super.move();
    if (y > (height + side)) {
      y = -side;
      x = random(0, width);
    }
  }

  public void display() {
    pushMatrix();
    translate(x-(side/2), y-(side/2));
    setColour();
    rotate(rotation);
    rect(0, 0, side, side);
    popMatrix();
  }
}

class Triangle extends Shape {
  float side;
  float h;

  // Constructor
  Triangle(float sd) {
    super();
    side = sd;
    h = (float)(side * (Math.sqrt(3)/2));
  }

  public void move() {
    super.move();
    if (y > (height + h * 2)) {
      y = - h * 2;
      x = random(0, width);
    }
  }

  public void display() {
    pushMatrix();
    translate(x-side, y-side);
    setColour();
    rotate(rotation);
    triangle(0, -h, -side, h, side, h);
    popMatrix();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "create_confetti" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
