# Blaze [![Release](https://jitpack.io/v/MrExplode/Blaze.svg)](https://jitpack.io/#MrExplode/Blaze)
Lightweight animation library made mainly for OpenGL usage

## Installation
Maven:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.MrExplode</groupId>
    <artifactId>Blaze</artifactId>
    <version>2.1.1</version>
</dependency>
```
Gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.MrExplode:Blaze:2.1.1'
}
```

## Building
You can build the project by running `./gradlew build`

## Usage
Create an `Animator` instance, and update it in every render loop:
```java
Animator animator = new Animator();

//update in render loop
animator.update(partialTicks);
```
Creating and using animations:
```java
Animation animation1 = Animation.animation(Eases.LINEAR);
Animation animation2 = Animation.animation(Eases.SINE_IN_OUT, AnimationType.bouncing(), 0.1);

animator.start(animation1);

//use animation value in render call
//...
double animValue = animation1.value();
//eg. translate the matrix
GL11.glTranslate2d(x, y + animValue);
//...
```

Built-in animation types:

```java
AnimationType bouncing();
AnimationType bouncing(Ease backwardEase);
AnimationType once();
AnimationType once(Runnable runnable);
AnimationType loop(Ease backwardEase);
```

## License
```
MIT License

Copyright (c) 2025 SunStorm

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
