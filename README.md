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
    Blaze
    Copyright (C) 2021  SunStorm (aka. MrExplode)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
```
