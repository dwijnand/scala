// scalac: -Werror
sealed trait PowerSource
case object Petrol extends PowerSource
case object Pedal  extends PowerSource

sealed abstract class Vehicle { type A <: PowerSource }
case object Bicycle       extends Vehicle { type A = Pedal.type  }
case class Bus(fuel: Int) extends Vehicle { type A = Petrol.type }
case class Car(fuel: Int) extends Vehicle { type A = Petrol.type }

object Test {
  def refuel1[P <: Petrol.type](vehicle: Vehicle { type A = P }): Vehicle = vehicle match {
    case Car(_)  => Car(100)
    case Bus(_)  => Bus(100) // was: "unreachable code" warning 1
    case Bicycle => ???      // expected unreachable 1
  }

  def refuel2[P <: Petrol.type](vehicle: Vehicle { type A = P }): Vehicle = vehicle match {
    case Car(_)          => Car(100)
    case Bus(_)          => Bus(100) // was: "unreachable code" warning 2
    case _: Bicycle.type => ???      // expected unreachable 2
  }
}

class Test[P <: Petrol.type] {
  def refuel1(vehicle: Vehicle { type A = P }): Vehicle = vehicle match {
    case Car(_)  => Car(100)
    case Bus(_)  => Bus(100) // was: "unreachable code" warning 3
    case Bicycle => ???      // expected unreachable 3
  }

  def refuel2(vehicle: Vehicle { type A = P }): Vehicle = vehicle match {
    case Car(_)          => Car(100)
    case Bus(_)          => Bus(100) // was: "unreachable code" warning 4
    case _: Bicycle.type => ???      // expected unreachable 4
  }
}
