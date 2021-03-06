package services.pagination

/**
 * @author VKoulakov
 * @since 24.03.2016 16:37
 */
trait PaginationSupport {
  def paginate[T](items : Iterable[T], page: Int = 0, totalF : => Long) = new Page(page, totalF, items.toList)
}

case class Page[T](page: Int, total: Long, items: List[T])