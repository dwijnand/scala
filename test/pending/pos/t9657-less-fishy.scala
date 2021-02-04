// scalac: -Werror

// like pos/t9657
// but with a more plausible reason for why to have a method type parameter

sealed trait PowerSource
sealed trait NonRenewablePowerSource extends PowerSource

case object Petrol extends NonRenewablePowerSource
case object Coal   extends NonRenewablePowerSource
case object Pedal  extends PowerSource

sealed abstract class Vehicle { type A <: PowerSource }
case object Bicycle       extends Vehicle { type A = Pedal.type  }
case class Bus(fuel: Int) extends Vehicle { type A = Petrol.type }
case class Car(fuel: Int) extends Vehicle { type A = Petrol.type }

class Test[P <: NonRenewablePowerSource] {
  def refuel(vehicle: Vehicle { type A = P }): Vehicle = vehicle match {
    case Car(_) => Car(100)
    case Bus(_) => Bus(100) // was: "unreachable code" warning 2
  }
}
