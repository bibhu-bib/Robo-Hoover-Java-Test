# Java Backend Homework

## Introduction

Write a service that navigates a imaginary robotic hoover (similar to a Roomba)
through an equally imaginary room where:

- The room dimensions are represented as `x` and `y` coordinates, identifying the
  top right corner of the room rectangle.

  The room is divided up in a grid based on these dimensions, e.g. a room that
  has dimensions `x = 5` and `y = 5` has **5 columns** and **5 rows**, so **25**
  possible hoover positions.

  The bottom left corner is the point of origin of the coordinate system and is
  represented by `x = 0` and `y = 0`.

- Locations of patches of dirt are also defined by the same `x` and `y`
  coordinates.

- The hoover is placed on an initial position (same `x` and `y` coordinates)

- The driving instructions are provided as cardinal directions (`N/S/E/W`), e.g.
  `N` and `E` mean "go north" and "go east" respectively

The room is to be rectangular, have no obstacles (except the room walls), no
doors and all locations in the room are assumed clean (hoovering has no effect)
unless it's a location of the patch of dirt (specified in the program input).

Placing the hoover on a patch of dirt ("hoovering") removes the patch of dirt
so that patch is then clean for the remainder of the program run.

The hoover is always on - there is no need to enable it.

Driving into a wall has no effect (the hoover skids in place).

## Goal

Implement an web service that takes the room dimensions, the locations of the
dirt patches, the hoover location and the driving instructions as input and
outputs the following:

- The final hoover position (`x`, `y`)
- The number of patches of dirt the hoover cleaned up

The service must persist every input and output to a database and allow those
records to be later retrieved (use best judgemnt for creating that API).

## Input

Program input must received as a json payload, e.g.

```json
{
  "roomSize" : [5, 5],
  "coords" : [1, 2],
  "patches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "instructions" : "NNESEESWNWW"
}
```

## Output

Service output must be returned as a json payload, e.g. (matching the input
above):

```json
{
  "coords" : [1, 3],
  "patches" : 1
}
```

{
"hooverRequest" {}
"hooverResponse: {}

}

Where `coords` are the final coordinates of the hoover and `patches` the
number patches of dirt the hoover cleaned up.

## Deliverable

The application:

- must be written in Java
- should use maven as the build system (gradle is also ok)
- must be web service
- must runnable on Linux (x86-64)
- can make use of any existing open source libraries/frameworks that don't
  directly address the problem statement (use your best judgement)

Send us:

- The full source code, including any code written which is not part of the
  normal application run (e.g. scripts)
- Clear instructions on how to obtain/build and run the application
- Please provide any deliverables and instructions using a public Github (or
  similar) repository or some other publicly accessible resource as several
  people will need to inspect the solution

## Evaluation

The goal of this exercise is for us to see code written by you (and should be
proud of). We will especially consider:

- Solving all functional requirements (as specified in this document)
- Code organisation, quality and readability

Consider it a way to showcase your ability to write clean, understandable and
maintainable code that properly addresses documented requirements in a reasonable
time frame.

This test is based on the following [gist](https://gist.github.com/alirussell/9a519e07128b7eafcb50)
