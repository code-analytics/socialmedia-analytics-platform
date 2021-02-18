def myFunc(func: (Int, Int) => Int, x: Int, y: Int): Int = {
  10 * func(x, y)
}

def passedFunc(x: Int, y: Int): Int = x + y

println(myFunc(passedFunc, 5, 10))